package com.redemonitor.disp_monitor.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import com.redemonitor.disp_monitor.exception.BusinessException;
import com.redemonitor.disp_monitor.exception.Errors;
import com.redemonitor.disp_monitor.integration.ConfigIntegration;
import com.redemonitor.disp_monitor.integration.DispositivoIntegration;
import com.redemonitor.disp_monitor.integration.EventoIntegration;
import com.redemonitor.disp_monitor.messaging.DispositivoMessageService;
import com.redemonitor.disp_monitor.model.Config;
import com.redemonitor.disp_monitor.model.Dispositivo;
import com.redemonitor.disp_monitor.service.device.DispositivoMonitor;
import com.redemonitor.disp_monitor.service.device.DispositivoMonitorThread;

@Service
public class DispositivoMonitorService {
	
    @Autowired
    private ConfigIntegration configRepository;

    @Autowired
    private DispositivoIntegration dispositivoRepository;

    @Autowired
    private EventoIntegration eventoRepository;

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @Autowired
    private DispositivoMessageService dispositivoMessageService;

    private final Map<Long, DispositivoMonitor> dispositivoMonitorMap = new ConcurrentHashMap<>();

    public void startMonitoramento( Long dispositivoId, String accessToken ) {
        Config config = configRepository.getConfig( accessToken );

        Dispositivo dispositivo = dispositivoRepository.getDispositivo( dispositivoId, accessToken );
              
        if ( dispositivoMonitorMap.containsKey( dispositivoId ) ) {
            dispositivo.setSendoMonitorado( true );
            dispositivoRepository.saveDispositivo( dispositivo, accessToken );

            dispositivoMessageService.sendMessage( dispositivo, accessToken ); 

            throw new BusinessException( Errors.DISPOSITIVO_ALREADY_MONITORED );
        }

        Duration monitorDelay = Duration.ofMillis( config.getMonitoramentoDelay() );

        DispositivoMonitorThread thread = new DispositivoMonitorThread(
                dispositivo, config, dispositivoRepository, eventoRepository, dispositivoMessageService, accessToken );

        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate( thread, Instant.now(), monitorDelay  );

        DispositivoMonitor dispositivoMonitor = new DispositivoMonitor( thread, scheduledFuture );
        dispositivoMonitorMap.put( dispositivoId, dispositivoMonitor );

        dispositivo.setSendoMonitorado( true );
        dispositivoRepository.saveDispositivo( dispositivo, accessToken );

        dispositivoMessageService.sendMessage( dispositivo, accessToken );
    }

    public void stopMonitoramento( Long dispositivoId, String accessToken ) {
        Dispositivo dispositivo = dispositivoRepository.getDispositivo( dispositivoId, accessToken );
        
        if ( !dispositivoMonitorMap.containsKey( dispositivoId ) ) {
            dispositivo.setSendoMonitorado( false );
            dispositivoRepository.saveDispositivo( dispositivo, accessToken );

            dispositivoMessageService.sendMessage( dispositivo, accessToken );

            throw new BusinessException( Errors.DISPOSITIVO_NOT_MONITORED );
        }

        DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
        dispositivoMonitor.getScheduledFuture().cancel( true );

        dispositivoMonitorMap.remove( dispositivoId );

        dispositivo.setSendoMonitorado( false );
        dispositivoRepository.saveDispositivo( dispositivo, accessToken );

        dispositivoMessageService.sendMessage( dispositivo, accessToken );
    }

    public void updateConfigInMonitores( String accessToken ) {
        Config config = configRepository.getConfig( accessToken );

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

    public void updateDispositivoInMonitor( Long dispositivoId, String accessToken ) {
        Dispositivo dispositivo = dispositivoRepository.getDispositivo( dispositivoId, accessToken );
       
        DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
        if ( dispositivoMonitor != null )
            dispositivoMonitor.getDeviceMonitorThread().setDispositivo( dispositivo );
    }

}
