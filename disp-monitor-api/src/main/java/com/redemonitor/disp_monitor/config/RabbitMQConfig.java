package com.redemonitor.disp_monitor.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
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

	@Value("${config.rabbitmq.dispositivos.queue}")
	private String dispositivosQueue;
	
	@Value("${config.rabbitmq.dispositivos.exchange}")
	private String dispositivosExchange;
	
	@Value("${config.rabbitmq.dispositivos.routing-key}")
	private String dispositivosRoutingKey;
	
	@Bean
	Queue queue() {
		return new Queue( dispositivosQueue );
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange( dispositivosExchange );
	}
	
	@Bean
	Binding binding( Queue queue, DirectExchange exchange ) {
		return BindingBuilder.bind( queue ).to( exchange ).with( dispositivosRoutingKey );
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
