package com.italomonitor.main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.italomonitor.main.messaging.websocket.WSHandshakeHandler;
import com.italomonitor.main.messaging.websocket.WebSocketHandlerDecoratorFactory2;

/*
 * O tempo de expiração do refresh token define o tempo de vida da sessão do websocket
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private WSHandshakeHandler wsHandshakeHandler;
    
    @Autowired
    private WebSocketHandlerDecoratorFactory2 webSocketHandlerDecoratorFactory2;
    
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
        		.setHandshakeHandler( wsHandshakeHandler ) 
                .setAllowedOrigins( allowedOrigin )
                .withSockJS();
    }
    
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.addDecoratorFactory( webSocketHandlerDecoratorFactory2 );
	}
		
}
