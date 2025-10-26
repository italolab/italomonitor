package com.redemonitor.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.redemonitor.exception.BusinessException;
import com.redemonitor.exception.Errors;
import com.redemonitor.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
