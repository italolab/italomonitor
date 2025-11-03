package com.redemonitor.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.redemonitor.main.components.util.JwtTokenUtil;
import com.redemonitor.main.exception.BusinessException;
import com.redemonitor.main.exception.Errors;

@Service
public class TokenService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    public String getUsernameByAuthorizationHeader( String authorizationHeader ) {
    	String accessToken = this.extractAccessToken( authorizationHeader );
    	return this.getUsernameByAccessToken( accessToken );
    }
    
    public String getUsernameByAccessToken( String accessToken ) {
    	try {        	        	
            DecodedJWT decodedJWT = jwtTokenUtil.verifyToken( accessToken );
            return decodedJWT.getSubject();
        } catch ( JWTVerificationException e ) {
            throw new BusinessException( Errors.NOT_AUTHORIZED );
        }
    }
    
    public String extractAccessToken( String authorizationHeader ) {
        if ( authorizationHeader != null )
        	if ( authorizationHeader.length() > 7 )
        		return authorizationHeader.substring( 7 );        
        return null;
    }
    
}
