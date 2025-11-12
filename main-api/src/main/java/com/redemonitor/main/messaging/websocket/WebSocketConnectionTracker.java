package com.redemonitor.main.messaging.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketConnectionTracker {
		
	private Map<String, String> connectedUsers = new ConcurrentHashMap<>();
	
	@EventListener
	public void handleConnectedListener( SessionConnectedEvent event ) {
		StompHeaderAccessor acessor = StompHeaderAccessor.wrap( event.getMessage() );
		
		connectedUsers.put( acessor.getSessionId(), acessor.getUser().getName() );		
	}
	
	@EventListener
	public void handleDisconnectedListener( SessionDisconnectEvent event ) {
		StompHeaderAccessor acessor = StompHeaderAccessor.wrap( event.getMessage() );

		connectedUsers.remove( acessor.getSessionId() );
	}
	
	public boolean verifySeConnected( String username ) {
		return connectedUsers.containsValue( username );
	}
	
}
