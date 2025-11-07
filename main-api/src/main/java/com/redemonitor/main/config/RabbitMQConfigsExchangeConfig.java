package com.redemonitor.main.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigsExchangeConfig {
	
	@Value("${config.rabbitmq.configs.exchange}")
	private String configsExchange;
	
	@Value("${config.rabbitmq.configs.queue1}")
	private String configsQueue1;
	
	@Value("${config.rabbitmq.configs.queue2}")
	private String configsQueue2;
	
	@Value("${config.rabbitmq.configs.queue3}")
	private String configsQueue3;
				
	@Bean
	Declarables configsDeclarables() {
		FanoutExchange exchange = new FanoutExchange( configsExchange );
		
		Map<String, Object> queueArgs = new HashMap<>();
		queueArgs.put( "x-queue-type", "quorum" ); 
		
		Queue queue1 = new Queue( configsQueue1, true, false, false, queueArgs );
		Queue queue2 = new Queue( configsQueue2, true, false, false, queueArgs );
		Queue queue3 = new Queue( configsQueue3, true, false, false, queueArgs );
		
		Binding binding1 = BindingBuilder.bind( queue1 ).to( exchange );
		Binding binding2 = BindingBuilder.bind( queue2 ).to( exchange );
		Binding binding3 = BindingBuilder.bind( queue3 ).to( exchange );
		
		return new Declarables( exchange, queue1, queue2, queue3, binding1, binding2, binding3 );
	}
	
}
