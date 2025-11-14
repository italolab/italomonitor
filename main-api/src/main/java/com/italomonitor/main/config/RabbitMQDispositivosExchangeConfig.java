package com.italomonitor.main.config;

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
public class RabbitMQDispositivosExchangeConfig {
	
	@Value("${config.rabbitmq.dispositivos.exchange}")
	private String dispositivosExchange;
	
	@Value("${config.rabbitmq.dispositivos.queue1}")
	private String dispositivosQueue1;
	
	@Value("${config.rabbitmq.dispositivos.queue2}")
	private String dispositivosQueue2;
	
	@Value("${config.rabbitmq.dispositivos.queue3}")
	private String dispositivosQueue3;
				
	@Bean
	Declarables dispositivosDeclarables() {
		FanoutExchange exchange = new FanoutExchange( dispositivosExchange );
		
		Map<String, Object> queueArgs = new HashMap<>();
		queueArgs.put( "x-queue-type", "quorum" ); 
		
		Queue queue1 = new Queue( dispositivosQueue1, true, false, false, queueArgs );
		Queue queue2 = new Queue( dispositivosQueue2, true, false, false, queueArgs );
		Queue queue3 = new Queue( dispositivosQueue3, true, false, false, queueArgs );
		
		Binding binding1 = BindingBuilder.bind( queue1 ).to( exchange );
		Binding binding2 = BindingBuilder.bind( queue2 ).to( exchange );
		Binding binding3 = BindingBuilder.bind( queue3 ).to( exchange );
		
		return new Declarables( exchange, queue1, queue2, queue3, binding1, binding2, binding3 );
		
	}
		
}
