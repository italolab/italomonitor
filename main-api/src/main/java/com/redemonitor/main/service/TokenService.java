package com.redemonitor.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.redemonitor.main.components.JwtTokenUtil;
import com.redemonitor.main.exception.BusinessException;
import com.redemonitor.main.exception.Errors;

@Service
public class TokenService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    public String getUsernameByAccessToken( String accessToken ) {
    	try {        	        	
            DecodedJWT decodedJWT = jwtTokenUtil.verifyToken( accessToken );
            return decodedJWT.getSubject();
        } catch ( JWTVerificationException e ) {
            throw new BusinessException( Errors.NOT_AUTHORIZED );
        }
    }
    
}
