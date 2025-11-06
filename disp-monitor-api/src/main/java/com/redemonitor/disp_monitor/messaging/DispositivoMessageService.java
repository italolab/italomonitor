package com.redemonitor.disp_monitor.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.redemonitor.disp_monitor.dto.message.DispositivoMessage;
import com.redemonitor.disp_monitor.model.Dispositivo;

@Service
public class DispositivoMessageService {
			
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${config.rabbitmq.dispositivos.exchange}")
	private String dispositivosExchange;
	
	@Value("${config.rabbitmq.dispositivos.routing-key}") 
	private String dispositivosRoutingKey;
	
	public void sendMessage( Dispositivo dispositivo ) {		
		DispositivoMessage message = DispositivoMessage.builder()
				.id( dispositivo.getId() )
				.status( dispositivo.getStatus() ) 
				.latenciaMedia( dispositivo.getLatenciaMedia() ) 
				.build();
		
		rabbitTemplate.convertAndSend( dispositivosExchange, dispositivosRoutingKey, message ); 
    }

}
