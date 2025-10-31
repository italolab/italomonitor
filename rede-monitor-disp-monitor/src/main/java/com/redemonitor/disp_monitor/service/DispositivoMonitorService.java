package com.redemonitor.disp_monitor.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import com.redemonitor.disp_monitor.exception.BusinessException;
import com.redemonitor.disp_monitor.exception.Errors;
import com.redemonitor.disp_monitor.integration.ConfigIntegration;
import com.redemonitor.disp_monitor.integration.DispositivoIntegration;
import com.redemonitor.disp_monitor.integration.EventoIntegration;
import com.redemonitor.disp_monitor.model.Config;
import com.redemonitor.disp_monitor.model.Dispositivo;
import com.redemonitor.disp_monitor.service.device.DispositivoMonitor;
import com.redemonitor.disp_monitor.service.device.DispositivoMonitorThread;
import com.redemonitor.disp_monitor.service.message.DispositivoMessageService;

@Service
public class DispositivoMonitorService {

	@Value("${ping.os}")
	private String pingOS;
	
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

    public void startMonitoramento( Long dispositivoId, String username ) {
        Config config = configRepository.getConfig();

        Dispositivo dispositivo = dispositivoRepository.getDispositivo( dispositivoId );
       
        if ( dispositivoMonitorMap.containsKey( dispositivoId ) ) {
            dispositivo.setSendoMonitorado( true );
            dispositivoRepository.saveDispositivo( dispositivo );

            dispositivoMessageService.sendMessage( dispositivo, username ); 

            throw new BusinessException( Errors.DISPOSITIVO_ALREADY_MONITORED );
        }

        Duration monitorDelay = Duration.ofMillis( config.getMonitoramentoDelay() );

        DispositivoMonitorThread thread = new DispositivoMonitorThread(
                dispositivo, config, dispositivoRepository, eventoRepository, dispositivoMessageService, username, pingOS );

        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate( thread, Instant.now(), monitorDelay  );

        DispositivoMonitor dispositivoMonitor = new DispositivoMonitor( thread, scheduledFuture );
        dispositivoMonitorMap.put( dispositivoId, dispositivoMonitor );

        dispositivo.setSendoMonitorado( true );
        dispositivoRepository.saveDispositivo( dispositivo );

        dispositivoMessageService.sendMessage( dispositivo, username );
    }

    public void stopMonitoramento( Long dispositivoId, String username ) {
        Dispositivo dispositivo = dispositivoRepository.getDispositivo( dispositivoId );
        
        if ( !dispositivoMonitorMap.containsKey( dispositivoId ) ) {
            dispositivo.setSendoMonitorado( false );
            dispositivoRepository.saveDispositivo( dispositivo );

            dispositivoMessageService.sendMessage( dispositivo, username );

            throw new BusinessException( Errors.DISPOSITIVO_NOT_MONITORED );
        }

        DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
        dispositivoMonitor.getScheduledFuture().cancel( true );

        dispositivoMonitorMap.remove( dispositivoId );

        dispositivo.setSendoMonitorado( false );
        dispositivoRepository.saveDispositivo( dispositivo );

        dispositivoMessageService.sendMessage( dispositivo, username );
    }

    public void setConfigInMonitores() {
        Config config = configRepository.getConfig();

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
        Dispositivo dispositivo = dispositivoRepository.getDispositivo( dispositivoId );
       
        DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
        if ( dispositivoMonitor != null )
            dispositivoMonitor.getDeviceMonitorThread().setDispositivo( dispositivo );
    }

}
