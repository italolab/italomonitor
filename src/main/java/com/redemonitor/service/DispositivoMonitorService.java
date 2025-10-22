package com.redemonitor.service;

import com.redemonitor.exception.BusinessException;
import com.redemonitor.exception.Errors;
import com.redemonitor.model.Config;
import com.redemonitor.model.Dispositivo;
import com.redemonitor.repository.ConfigRepository;
import com.redemonitor.repository.DispositivoRepository;
import com.redemonitor.service.device.DispositivoMonitor;
import com.redemonitor.service.device.DispositivoMonitorThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
    private ThreadPoolTaskScheduler scheduler;

    private Map<Long, DispositivoMonitor> dispositivoMonitorMap = new ConcurrentHashMap<>();

    public void startMonitoramento( Long dispositivoId ) {
        if ( dispositivoMonitorMap.containsKey( dispositivoId ) )
            throw new BusinessException(Errors.DISPOSITIVO_ALREADY_MONITORED );

        Config config = configRepository.findFirstByOrderByIdAsc();

        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
        if ( dispositivoOp.isEmpty() )
            throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );

        Dispositivo dispositivo = dispositivoOp.get();

        Duration monitorDelay = Duration.ofSeconds( config.getMonitoramentoDelay() );

        DispositivoMonitorThread thread = new DispositivoMonitorThread( dispositivo, config, dispositivoRepository );
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleWithFixedDelay( thread, monitorDelay  );

        DispositivoMonitor dispositivoMonitor = new DispositivoMonitor( thread, scheduledFuture );
        dispositivoMonitorMap.put( dispositivoId, dispositivoMonitor );

        dispositivo.setSendoMonitorado( true );
        dispositivoRepository.save( dispositivo );
    }

    public void stopMonitoramento( Long dispositivoId ) {
        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
        if ( dispositivoOp.isEmpty() )
            throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );

        if ( !dispositivoMonitorMap.containsKey( dispositivoId ) )
            throw new BusinessException( Errors.DISPOSITIVO_NOT_MONITORED );

        DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
        dispositivoMonitor.getScheduledFuture().cancel( true );

        dispositivoMonitorMap.remove( dispositivoId );

        Dispositivo dispositivo = dispositivoOp.get();
        dispositivo.setSendoMonitorado( false );
        dispositivoRepository.save( dispositivo );
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
