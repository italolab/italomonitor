package com.italomonitor.main.messaging.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.italomonitor.main.dto.integration.DispMonitorConfig;
import com.italomonitor.main.mapper.ConfigMapper;
import com.italomonitor.main.model.Config;

@Component
public class ConfigMessageSender {

	@Value("${config.rabbitmq.configs.exchange}")
	private String configsExchange;
		
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ConfigMapper configMapper;
	
	public void sendMessage( Config config ) {
		DispMonitorConfig dmConfig = configMapper.mapToDispMonitorConfig( config );
				
		rabbitTemplate.convertAndSend( configsExchange, "", dmConfig );
	}
	
}
