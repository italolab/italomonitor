package com.italomonitor.disp_monitor.components.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtTokenUtil {
	
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.issuer}")
    private String issuer;
    
    public DecodedJWT verifyToken( String token ) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256( secretKey );

        JWTVerifier verifier = JWT.require( algorithm )
                .withIssuer( issuer )
                .build();

        return verifier.verify( token );
    }

}
