package italo.italomonitor.disp_monitor.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import italo.italomonitor.disp_monitor.dto.request.StartMonitoramentoRequest;
import italo.italomonitor.disp_monitor.dto.response.ExisteNoMonitorResponse;
import italo.italomonitor.disp_monitor.dto.response.MonitoramentoOperResponse;
import italo.italomonitor.disp_monitor.enums.MonitoramentoOperResult;
import italo.italomonitor.disp_monitor.lib.to.Config;
import italo.italomonitor.disp_monitor.lib.to.Dispositivo;
import italo.italomonitor.disp_monitor.messaging.sender.DispositivoStateMessageSender;
import italo.italomonitor.disp_monitor.messaging.sender.EventoMessageSender;
import italo.italomonitor.disp_monitor.service.device.DispositivoMonitor;
import italo.italomonitor.disp_monitor.service.device.DispositivoMonitorManager;

@Service
public class DispositivoMonitorService {
	
    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @Autowired
    private DispositivoStateMessageSender dispositivoStateMessageService;
    
    @Autowired
    private EventoMessageSender eventoMessageService;
    
    private final Map<Long, DispositivoMonitor> dispositivoMonitorMap = new ConcurrentHashMap<>();    
       
    public MonitoramentoOperResponse startMonitoramento( StartMonitoramentoRequest request ) {
    	Config config = request.getConfig();
        Dispositivo dispositivo = request.getDispositivo();
                
        if ( dispositivo.isMonitoradoPorAgente() )
        	return MonitoramentoOperResponse.builder()
            		.result( MonitoramentoOperResult.MONITORADO_POR_AGENTE )
            		.build();
        
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

        DispositivoMonitorManager manager = new DispositivoMonitorManager(
                dispositivo, config, dispositivoStateMessageService, eventoMessageService );

        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate( manager.getRunnable(), Instant.now(), monitorDelay  );

        DispositivoMonitor dispositivoMonitor = new DispositivoMonitor( manager, scheduledFuture );
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
        
		DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.remove( dispositivoId );        
        dispositivoMonitor.getScheduledFuture().cancel( true );
        
        return MonitoramentoOperResponse.builder()
        		.result( MonitoramentoOperResult.FINALIZADO )
        		.build(); 
    }
    
    public void stopAllMonitoramentos() {
    	Set<Long> dispIDs = dispositivoMonitorMap.keySet();
    	for( Long dispositivoId : dispIDs ) {
    		DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.remove( dispositivoId );            
            dispositivoMonitor.getScheduledFuture().cancel( true );            
    	}
    }

    public void updateConfigInMonitores( Config config ) {    	    
        Set<Long> ids = dispositivoMonitorMap.keySet();
        for( Long dispositivoId : ids ) {
            DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
            if ( dispositivoMonitor != null ) {
            	Config conf = dispositivoMonitor.getDeviceMonitorManager().getRunnable().getConfig();            	
             
            	dispositivoMonitor.getDeviceMonitorManager().getRunnable().setConfig( config );
                
                if ( conf.getMonitoramentoDelay() != config.getMonitoramentoDelay() ) {                	
	                dispositivoMonitor.getScheduledFuture().cancel( true );
	
	                Duration monitorDelay = Duration.ofMillis( config.getMonitoramentoDelay() );
	
	                ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate( 
	                		dispositivoMonitor.getDeviceMonitorManager().getRunnable(), Instant.now(), monitorDelay  );
	                
	                dispositivoMonitor.setScheduledFuture( scheduledFuture ); 
                }
            }
        }                
    }

    public void updateDispositivoInMonitor( Dispositivo dispositivo ) {
    	Long dispositivoId = dispositivo.getId();
    	    	    	
        DispositivoMonitor dispositivoMonitor = dispositivoMonitorMap.get( dispositivoId );
        if ( dispositivoMonitor != null )
            dispositivoMonitor.getDeviceMonitorManager().getRunnable().setDispositivo( dispositivo );                                               
    }
        
    public ExisteNoMonitorResponse existeNoMonitor( Long dispositivoId ) {
    	boolean existe = dispositivoMonitorMap.containsKey( dispositivoId );
    	
    	return ExisteNoMonitorResponse.builder()
    			.existe( existe )
     			.build();
    }

    public int getNumThreadsAtivas() {
    	return dispositivoMonitorMap.size();
    }
    
}
