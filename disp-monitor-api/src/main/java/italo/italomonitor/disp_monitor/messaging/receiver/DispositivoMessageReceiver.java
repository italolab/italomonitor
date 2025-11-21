package italo.italomonitor.disp_monitor.messaging.receiver;

import org.jboss.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import italo.italomonitor.disp_monitor.exception.ErrorException;
import italo.italomonitor.disp_monitor.lib.to.Dispositivo;
import italo.italomonitor.disp_monitor.service.DispositivoMonitorService;

@Component
public class DispositivoMessageReceiver {

	@Autowired
	private DispositivoMonitorService dispositivoMonitorService;
	
	@RabbitListener( queues = {"${config.rabbitmq.dispositivos.queue}"} ) 
	public void receivesMessage( @Payload Dispositivo message ) {
		try {
			dispositivoMonitorService.updateDispositivoInMonitor( message );
		} catch ( ErrorException e ) {
			Logger.getLogger( DispositivoMonitorService.class ).error( e.response().getMessage() ); 
		}
	}
	
}
