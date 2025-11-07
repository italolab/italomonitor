package com.redemonitor.main.messaging.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.redemonitor.main.dto.integration.DispMonitorDispositivo;
import com.redemonitor.main.mapper.DispositivoMapper;
import com.redemonitor.main.model.Dispositivo;

@Component
public class DispositivoMessageSender {

	@Value("${config.rabbitmq.dispositivos.exchange}")
	private String dispositivosExchange;
		
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private DispositivoMapper dispositivoMapper;
	
	public void sendMessage( Dispositivo dispositivo ) {
		DispMonitorDispositivo dmDispositivo = dispositivoMapper.mapToDispMonitorDispositivo( dispositivo );
		
		rabbitTemplate.convertAndSend( dispositivosExchange, "", dmDispositivo );
	}
	
}

