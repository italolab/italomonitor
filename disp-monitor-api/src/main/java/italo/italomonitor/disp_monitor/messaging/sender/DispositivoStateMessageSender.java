package italo.italomonitor.disp_monitor.messaging.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import italo.italomonitor.disp_monitor.lib.to.DispositivoState;

@Service
public class DispositivoStateMessageSender {
			
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${config.rabbitmq.dispositivos-state.exchange}")
	private String dispositivosExchange;
	
	@Value("${config.rabbitmq.dispositivos-state.routing-key}") 
	private String dispositivosRoutingKey;
	
	public void sendMessage( DispositivoState message ) {						
		rabbitTemplate.convertAndSend( dispositivosExchange, dispositivosRoutingKey, message ); 
    }

}
