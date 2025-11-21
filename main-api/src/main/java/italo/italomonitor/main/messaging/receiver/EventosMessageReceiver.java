package italo.italomonitor.main.messaging.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import italo.italomonitor.main.dto.integration.DispMonitorEvento;
import italo.italomonitor.main.messaging.receiver.processor.EventoMessageProcessor;

@Component
public class EventosMessageReceiver {
	
	@Autowired
	private EventoMessageProcessor eventoMessageProcessor;
		
	@RabbitListener( queues = {"${config.rabbitmq.eventos.queue}"} ) 
	public void receivesMessage( @Payload DispMonitorEvento message ) {
		eventoMessageProcessor.processMessage( message );
	}
	
}
