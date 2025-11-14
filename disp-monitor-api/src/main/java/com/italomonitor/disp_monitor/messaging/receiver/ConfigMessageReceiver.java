package com.italomonitor.disp_monitor.messaging.receiver;

import org.jboss.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.italomonitor.disp_monitor.dto.Config;
import com.italomonitor.disp_monitor.exception.ErrorException;
import com.italomonitor.disp_monitor.service.DispositivoMonitorService;

@Component
public class ConfigMessageReceiver {

	@Autowired
	private DispositivoMonitorService dispositivoMonitorService;
	
	@RabbitListener( queues = {"${config.rabbitmq.configs.queue}"} ) 
	public void receivesMessage( @Payload Config message ) {			
		try {			
			dispositivoMonitorService.updateConfigInMonitores( message );
		} catch ( ErrorException e ) {
			Logger.getLogger( DispositivoMonitorService.class ).error( e.response().getMessage() ); 
		}
	}
	
}
