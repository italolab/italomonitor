package com.redemonitor.disp_monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.redemonitor.disp_monitor.exception.BusinessException;
import com.redemonitor.disp_monitor.exception.Errors;
import com.redemonitor.disp_monitor.util.JwtTokenUtil;

@Service
public class TokenService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String getUsername( String token ) {
        try {
            DecodedJWT decodedJWT = jwtTokenUtil.verifyToken( token );
            return decodedJWT.getSubject();
        } catch ( JWTVerificationException e ) {
            throw new BusinessException( Errors.NOT_AUTHORIZED );
        }
    }

}
