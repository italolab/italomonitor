package com.italomonitor.disp_monitor.messaging.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.italomonitor.disp_monitor.dto.Dispositivo;
import com.italomonitor.disp_monitor.dto.DispositivoState;

@Service
public class DispositivoStateMessageSender {
			
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${config.rabbitmq.dispositivos-state.exchange}")
	private String dispositivosExchange;
	
	@Value("${config.rabbitmq.dispositivos-state.routing-key}") 
	private String dispositivosRoutingKey;
	
	public void sendMessage( Dispositivo dispositivo ) {		
		DispositivoState message = DispositivoState.builder()
				.id( dispositivo.getId() )
				.status( dispositivo.getStatus() ) 
				.latenciaMedia( dispositivo.getLatenciaMedia() ) 
				.build();
		
		rabbitTemplate.convertAndSend( dispositivosExchange, dispositivosRoutingKey, message ); 
    }

}
