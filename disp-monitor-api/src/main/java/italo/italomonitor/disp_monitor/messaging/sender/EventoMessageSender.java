package italo.italomonitor.disp_monitor.messaging.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import italo.italomonitor.disp_monitor.lib.to.Evento;

@Service
public class EventoMessageSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${config.rabbitmq.eventos.exchange}")
	private String eventosExchange;
	
	@Value("${config.rabbitmq.eventos.routing-key}") 
	private String eventosRoutingKey;
	
	public void sendMessage( Evento message ) {	
		rabbitTemplate.convertAndSend( eventosExchange, eventosRoutingKey, message ); 
    }

}
