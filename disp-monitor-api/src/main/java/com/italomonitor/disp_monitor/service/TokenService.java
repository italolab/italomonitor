package com.italomonitor.disp_monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.italomonitor.disp_monitor.components.util.JwtTokenUtil;
import com.italomonitor.disp_monitor.exception.BusinessException;
import com.italomonitor.disp_monitor.exception.Errors;

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
