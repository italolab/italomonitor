package com.redemonitor.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import com.redemonitor.main.exception.BusinessException;
import com.redemonitor.main.exception.Errors;
import com.redemonitor.main.model.Config;
import com.redemonitor.main.model.Dispositivo;
import com.redemonitor.main.model.enums.DispositivoStatus;
import com.redemonitor.main.repository.ConfigRepository;
import com.redemonitor.main.repository.DispositivoRepository;
import com.redemonitor.main.repository.EventoRepository;
import com.redemonitor.main.service.device.DispositivoMonitor;
import com.redemonitor.main.service.device.DispositivoMonitorThread;
import com.redemonitor.main.service.message.DispositivoMessageService;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class DispositivoMonitorService {

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @Autowired
    private DispositivoMessageService dispositivoMessageService;

    private final Map<Long, DispositivoMonitor> dispositivoMonitorMap = new ConcurrentHashMap<>();

    public void startMonitoramento( Long dispositivoId, String username ) {
        Config config = configRepository.findFirstByOrderByIdAsc();

        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
        if ( dispositivoOp.isEmpty() )
            throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );

        Dispositivo dispositivo = dispositivoOp.get();

        if ( dispositivoMonitorMap.containsKey( dispositivoId ) ) {
            dispositivo.setSendoMonitorado( true );
            dispositivoRepository.save( dispositivo );

            dispositivoMessageService.send( dispositivo, username );

            throw new BusinessException( Errors.DISPOSITIVO_ALREADY_MONITORED );
        }

        Duration monitorDelay = Duration.ofMillis( config.getMonitoramentoDelay() );

        DispositivoMonitorThread thread = new DispositivoMonitorThread(
                dispositivo, config, dispositivoRepository, eventoRepository, dispositivoMessageService, username );

        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate( thread, Instant.now(), monitorDelay  );

        DispositivoMonitor dispositivoMonitor = new DispositivoMonitor( thread, scheduledFuture );
        dispositivoMonitorMap.put( dispositivoId, dispositivoMonitor );

        dispositivo.setSendoMonitorado( true );
        dispositivoRepository.save( dispositivo );

        dispositivoMessageService.send( dispositivo, username );
    }

    public void stopMonitoramento( Long dispositivoId, String username ) {
        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
        if ( dispositivoOp.isEmpty() )
            throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );

        Dispositivo dispositivo = dispositivoOp.get();

        if ( !dispositivoMonitorMap.containsKey( dispositivoId ) ) {
            dispositivo.setSendoMonitorado( false );
            dispositivoRepository.save( dispositivo );

            dispositivoMessageService.send( dispositivo, username );

            throw new BusinessException( Errors.DISPOSITIVO_NOT_MONITORED );
        }

        DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
        dispositivoMonitor.getScheduledFuture().cancel( true );

        dispositivoMonitorMap.remove( dispositivoId );

        dispositivo.setSendoMonitorado( false );
        dispositivoRepository.save( dispositivo );

        dispositivoMessageService.send( dispositivo, username );
    }

    public void setConfigInMonitores() {
        Config config = configRepository.findFirstByOrderByIdAsc();

        Set<Long> ids = dispositivoMonitorMap.keySet();
        for( Long dispositivoId : ids ) {
            DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
            if ( dispositivoMonitor != null ) {
                dispositivoMonitor.getDeviceMonitorThread().setConfig(config);
                dispositivoMonitor.getScheduledFuture().cancel( true );

                Duration monitorDelay = Duration.ofSeconds( config.getMonitoramentoDelay() );
                scheduler.scheduleWithFixedDelay( dispositivoMonitor.getDeviceMonitorThread(), monitorDelay );
            }
        }
    }

    public void setDeviceInMonitor( Long dispositivoId ) {
        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
        if ( dispositivoOp.isEmpty() )
            throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );

        Dispositivo dispositivo = dispositivoOp.get();

        DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
        if ( dispositivoMonitor != null )
            dispositivoMonitor.getDeviceMonitorThread().setDispositivo( dispositivo );
    }

}
