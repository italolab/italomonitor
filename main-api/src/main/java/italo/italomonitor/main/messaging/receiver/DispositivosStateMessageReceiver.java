package italo.italomonitor.main.messaging.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import italo.italomonitor.main.dto.integration.DispMonitorDispositivoState;
import italo.italomonitor.main.messaging.receiver.processor.DispositivoStateMessageProcessor;

@Component
public class DispositivosStateMessageReceiver {
		
	@Autowired
	private DispositivoStateMessageProcessor dispositivoStateMessageProcessor;
		
	@RabbitListener( queues = {"${config.rabbitmq.dispositivos-state.queue}"} ) 
	public void receivesMessage( @Payload DispMonitorDispositivoState message ) {
		dispositivoStateMessageProcessor.processMessage( message );
	}
		
}
