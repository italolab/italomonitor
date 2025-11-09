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

import com.redemonitor.disp_monitor.dto.Config;
import com.redemonitor.disp_monitor.dto.Dispositivo;
import com.redemonitor.disp_monitor.dto.request.StartMonitoramentoRequest;
import com.redemonitor.disp_monitor.dto.response.ExisteNoMonitorResponse;
import com.redemonitor.disp_monitor.dto.response.InfoResponse;
import com.redemonitor.disp_monitor.dto.response.MonitoramentoOperResponse;
import com.redemonitor.disp_monitor.enums.MonitoramentoOperResult;
import com.redemonitor.disp_monitor.messaging.sender.DispositivoStateMessageSender;
import com.redemonitor.disp_monitor.messaging.sender.EventoMessageSender;
import com.redemonitor.disp_monitor.service.device.DispositivoMonitor;
import com.redemonitor.disp_monitor.service.device.DispositivoMonitorThread;

@Service
public class DispositivoMonitorService {
	
    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @Autowired
    private DispositivoStateMessageSender dispositivoMessageService;
    
    @Autowired
    private EventoMessageSender eventoMessageService;
    
    private final Map<Long, DispositivoMonitor> dispositivoMonitorMap = new ConcurrentHashMap<>();    

    public MonitoramentoOperResponse startMonitoramento( StartMonitoramentoRequest request ) {
    	Config config = request.getConfig();
        Dispositivo dispositivo = request.getDispositivo();
                
    	Long dispositivoId = dispositivo.getId();

    	if ( dispositivoMonitorMap.containsKey( dispositivoId ) ) {
            return MonitoramentoOperResponse.builder()
            		.result( MonitoramentoOperResult.JA_INICIADO )
            		.build();
        }
    	
        if ( dispositivoMonitorMap.size() >= config.getNumThreadsLimite() ) {
        	return MonitoramentoOperResponse.builder()
        			.result( MonitoramentoOperResult.EXCEDE_LIMITE ) 
        			.build();
        }
              
        Duration monitorDelay = Duration.ofMillis( config.getMonitoramentoDelay() );

        DispositivoMonitorThread thread = new DispositivoMonitorThread(
                dispositivo, config, dispositivoMessageService, eventoMessageService );

        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate( thread, Instant.now(), monitorDelay  );

        DispositivoMonitor dispositivoMonitor = new DispositivoMonitor( thread, scheduledFuture );
        dispositivoMonitorMap.put( dispositivoId, dispositivoMonitor );
        
        return MonitoramentoOperResponse.builder()
    			.result( MonitoramentoOperResult.INICIADO ) 
    			.build();
    }

    public MonitoramentoOperResponse stopMonitoramento( Long dispositivoId ) {    	    	        
        if ( !dispositivoMonitorMap.containsKey( dispositivoId ) ) {
            return MonitoramentoOperResponse.builder()
            		.result( MonitoramentoOperResult.NAO_ENCONTRADO )
            		.build();
        }

        DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
        dispositivoMonitor.getScheduledFuture().cancel( true );

        dispositivoMonitorMap.remove( dispositivoId );
        
        return MonitoramentoOperResponse.builder()
        		.result( MonitoramentoOperResult.FINALIZADO )
        		.build(); 
    }

    public void updateConfigInMonitores( Config config ) {    	    
        Set<Long> ids = dispositivoMonitorMap.keySet();
        for( Long dispositivoId : ids ) {
            DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
            if ( dispositivoMonitor != null ) {
            	Config conf = dispositivoMonitor.getDeviceMonitorThread().getConfig();            	
             
            	dispositivoMonitor.getDeviceMonitorThread().setConfig( config );
                
                if ( conf.getMonitoramentoDelay() != config.getMonitoramentoDelay() ) {                	
	                dispositivoMonitor.getScheduledFuture().cancel( true );
	
	                Duration monitorDelay = Duration.ofMillis( config.getMonitoramentoDelay() );
	
	                ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate( 
	                		dispositivoMonitor.getDeviceMonitorThread(), Instant.now(), monitorDelay  );
	                
	                dispositivoMonitor.setScheduledFuture( scheduledFuture ); 
                }
            }
        }                
    }

    public void updateDispositivoInMonitor( Dispositivo dispositivo ) {
    	Long dispositivoId = dispositivo.getId();
    	    	    	
        DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
        if ( dispositivoMonitor != null )
            dispositivoMonitor.getDeviceMonitorThread().setDispositivo( dispositivo );                                               
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
