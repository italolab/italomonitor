package com.redemonitor.main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.redemonitor.main.security.WebSocketAuthInterceptor;

/*
 * O tempo de expiração do refresh token define o tempo de vida da sessão do websocket
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private WebSocketAuthInterceptor webSocketAuthInterceptor;
    
    @Value("${jwt.refresh_token.expire.at}")
    private String refreshTokenExpireAt;
    
    @Value("${cors.allowed.origin}")
    private String allowedOrigin;
    
    @Bean
    ServletServerContainerFactoryBean createWebSocketContainer() {
    	ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
    	container.setMaxSessionIdleTimeout( Integer.parseInt( refreshTokenExpireAt ) * 1000L ); 
    	return container;
    }
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker( "/topic" ); 
        registry.setUserDestinationPrefix( "/user" );
        registry.setApplicationDestinationPrefixes( "/app" );
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint( "/ws" )
                .setAllowedOrigins( allowedOrigin )
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors( webSocketAuthInterceptor );
    }

}
