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

import com.redemonitor.disp_monitor.dto.ExisteNoMonitorResponse;
import com.redemonitor.disp_monitor.dto.InfoResponse;
import com.redemonitor.disp_monitor.dto.MonitoramentoOperResponse;
import com.redemonitor.disp_monitor.enums.MonitoramentoOperResult;
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

    public MonitoramentoOperResponse startMonitoramento( Long dispositivoId ) {
    	if ( dispositivoMonitorMap.containsKey( dispositivoId ) ) {
            return MonitoramentoOperResponse.builder()
            		.result( MonitoramentoOperResult.JA_INICIADO )
            		.build();
        }
    	
        Config config = configRepository.getConfig();
        
        if ( dispositivoMonitorMap.size() >= config.getNumThreadsLimite() ) {
        	return MonitoramentoOperResponse.builder()
        			.result( MonitoramentoOperResult.EXCEDE_LIMITE ) 
        			.build();
        }
              
        Dispositivo dispositivo = dispositivoRepository.getDispositivo( dispositivoId );

        Duration monitorDelay = Duration.ofMillis( config.getMonitoramentoDelay() );

        DispositivoMonitorThread thread = new DispositivoMonitorThread(
                dispositivo, config, dispositivoRepository, eventoRepository, dispositivoMessageService );

        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate( thread, Instant.now(), monitorDelay  );

        DispositivoMonitor dispositivoMonitor = new DispositivoMonitor( thread, scheduledFuture );
        dispositivoMonitorMap.put( dispositivoId, dispositivoMonitor );

        dispositivo.setSendoMonitorado( true );
        dispositivoRepository.saveDispositivo( dispositivo );
        
        return MonitoramentoOperResponse.builder()
    			.result( MonitoramentoOperResult.INICIADO ) 
    			.build();
    }

    public MonitoramentoOperResponse stopMonitoramento( Long dispositivoId ) {    	    	
        Dispositivo dispositivo = dispositivoRepository.getDispositivo( dispositivoId );
        
        if ( !dispositivoMonitorMap.containsKey( dispositivoId ) ) {
            return MonitoramentoOperResponse.builder()
            		.result( MonitoramentoOperResult.NAO_ENCONTRADO )
            		.build();
        }

        DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
        dispositivoMonitor.getScheduledFuture().cancel( true );

        dispositivoMonitorMap.remove( dispositivoId );

        dispositivo.setSendoMonitorado( false );
        dispositivoRepository.saveDispositivo( dispositivo );
        
        return MonitoramentoOperResponse.builder()
        		.result( MonitoramentoOperResult.FINALIZADO )
        		.build(); 
    }

    public MonitoramentoOperResponse updateConfigInMonitores() {
        Config config = configRepository.getConfig();

        Set<Long> ids = dispositivoMonitorMap.keySet();
        for( Long dispositivoId : ids ) {
            DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
            if ( dispositivoMonitor != null ) {
                dispositivoMonitor.getDeviceMonitorThread().setConfig(config);
                dispositivoMonitor.getScheduledFuture().cancel( true );

                Duration monitorDelay = Duration.ofSeconds( config.getMonitoramentoDelay() );
                scheduler.scheduleWithFixedDelay( dispositivoMonitor.getDeviceMonitorThread(), monitorDelay );
                
                return MonitoramentoOperResponse.builder()
                		.result( MonitoramentoOperResult.ATUALIZADO )
                		.build();
            }
        }
        
        return MonitoramentoOperResponse.builder()
        		.result( MonitoramentoOperResult.NAO_ENCONTRADO )
        		.build();
    }

    public MonitoramentoOperResponse updateDispositivoInMonitor( Long dispositivoId ) {
        Dispositivo dispositivo = dispositivoRepository.getDispositivo( dispositivoId );
       
        DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
        if ( dispositivoMonitor != null ) {
            dispositivoMonitor.getDeviceMonitorThread().setDispositivo( dispositivo );
            
            return MonitoramentoOperResponse.builder()
            		.result( MonitoramentoOperResult.ATUALIZADO )
            		.build();
        }
        
        return MonitoramentoOperResponse.builder()
        		.result( MonitoramentoOperResult.NAO_ENCONTRADO ) 
        		.build();
    }
    
    public InfoResponse getInfo() {
    	return InfoResponse.builder()
    			.numThreadsAtivas( dispositivoMonitorMap.size() ) 
    			.build();
    }
    
    public ExisteNoMonitorResponse existeNoMonitor( Long dispositivoId ) {
    	boolean existe = dispositivoMonitorMap.containsKey( dispositivoId );
    	
    	return ExisteNoMonitorResponse.builder()
    			.existe( existe )
     			.build();
    }

}
