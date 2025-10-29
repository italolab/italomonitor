package com.redemonitor.main.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.redemonitor.main.service.LoginService;
import com.redemonitor.main.util.JwtTokenUtil;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LoginService loginService;

    @Override
    public @Nullable Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor( message, StompHeaderAccessor.class );

        if ( accessor != null ) {
            if ( StompCommand.CONNECT.equals( accessor.getCommand() ) ) {
                String authorizationHeader = accessor.getFirstNativeHeader( "Authorization" );

                if ( authorizationHeader != null ) {
                    String token = authorizationHeader.substring(7);

                    DecodedJWT decodedJWT = jwtTokenUtil.verifyToken( token );
                    if ( decodedJWT.getExpiresAt().after( new Date() ) ) {
                        accessor.setUser( decodedJWT::getSubject );
                        return message;
                    }
                    throw new MessageDeliveryException("Token inválido.");
                }
            }
        }

        return message;
    }
}
