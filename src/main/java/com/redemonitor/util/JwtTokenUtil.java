package com.redemonitor.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private long expireAt = 3600000l;

    @Value("${jwt.issuer}")
    private String issuer;

    public String createToken( String username, String[] roles ) {
        Algorithm algorithm = Algorithm.HMAC256( secretKey );

        return JWT.create()
                .withIssuer( issuer )
                .withSubject( username )
                .withArrayClaim( "roles", roles )
                .withExpiresAt( new Date( System.currentTimeMillis() + expireAt ) )
                .sign( algorithm );
    }

    public DecodedJWT verifyToken( String token ) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256( secretKey );

        JWTVerifier verifier = JWT.require( algorithm )
                .withIssuer( issuer )
                .build();

        return verifier.verify( token );
    }

}
