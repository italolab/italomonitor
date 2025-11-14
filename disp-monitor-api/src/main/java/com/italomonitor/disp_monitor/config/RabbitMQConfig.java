package com.italomonitor.disp_monitor.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${config.rabbitmq.dispositivos-state.queue}")
	private String dispositivosStateQueue;
	
	@Value("${config.rabbitmq.dispositivos-state.exchange}")
	private String dispositivosStateExchange;
	
	@Value("${config.rabbitmq.dispositivos-state.routing-key}")
	private String dispositivosStateRoutingKey;
	
	@Value("${config.rabbitmq.eventos.queue}")
	private String eventosQueue;
	
	@Value("${config.rabbitmq.eventos.exchange}")
	private String eventosExchange;
	
	@Value("${config.rabbitmq.eventos.routing-key}")
	private String eventosRoutingKey;
	
	@Bean(name="dispositivosStateQueue") 
	Queue dispositivosQueue() {
		return new Queue( dispositivosStateQueue );
	}
	
	@Bean
	Declarables dispositivosStateDeclarables() {
		DirectExchange exchange = new DirectExchange( dispositivosStateExchange );				
		Queue queue1 = new Queue( dispositivosStateQueue );		
		Binding binding1 = BindingBuilder.bind( queue1 ).to( exchange ).with( dispositivosStateRoutingKey );
		
		return new Declarables( exchange, queue1, binding1 );		
	}
	
	@Bean
	Declarables eventosDeclarables() {
		DirectExchange exchange = new DirectExchange( eventosExchange );				
		Queue queue1 = new Queue( eventosQueue );		
		Binding binding1 = BindingBuilder.bind( queue1 ).to( exchange ).with( eventosRoutingKey );
		
		return new Declarables( exchange, queue1, binding1 );		
	}
		
	@Bean
	MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	RabbitTemplate rabbitTemplate( ConnectionFactory connectionFactory ) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate( connectionFactory );
		rabbitTemplate.setMessageConverter( jsonMessageConverter() );
		return rabbitTemplate;
	}
	
}
