package com.italomonitor.main.messaging.websocket.handlers;

import java.security.Principal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.italomonitor.main.components.util.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class WSHandshakeHandler extends DefaultHandshakeHandler {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		
		HttpServletRequest servletRequest = ((ServletServerHttpRequest)request).getServletRequest();
		String authorizationHeader = servletRequest.getHeader( "Authorization" );

        if ( authorizationHeader != null ) {
            String token = jwtTokenUtil.extractAccessToken( authorizationHeader );
            try {
	            DecodedJWT decodedJWT = jwtTokenUtil.verifyToken( token );
	            if ( decodedJWT.getExpiresAt().after( new Date() ) )
	                return decodedJWT::getSubject;	            
            } catch ( JWTVerificationException e ) {
            	
            }
        }
		
		return () -> UUID.randomUUID().toString(); 
	}

}
