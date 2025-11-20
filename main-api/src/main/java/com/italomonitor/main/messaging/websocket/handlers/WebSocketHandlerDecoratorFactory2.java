package com.italomonitor.main.messaging.websocket.handlers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

@Component
public class WebSocketHandlerDecoratorFactory2 implements WebSocketHandlerDecoratorFactory {
	
	private Map<String, String> connectedUsers = new ConcurrentHashMap<>();

	@Override
	public WebSocketHandler decorate(WebSocketHandler handler) {
		return new WebSocketHandlerDecorator( handler ) {
			@Override
			public void afterConnectionEstablished(WebSocketSession session) throws Exception {
				connectedUsers.put( session.getId(), session.getPrincipal().getName() );
				super.afterConnectionEstablished( session );
			}

			@Override
			public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
				connectedUsers.remove( session.getId() );
				super.afterConnectionClosed( session, closeStatus ); 
			}								
		} ;
	}
		
	public boolean verifySeConnected( String username ) {
		return connectedUsers.containsValue( username );
	}
	
}
