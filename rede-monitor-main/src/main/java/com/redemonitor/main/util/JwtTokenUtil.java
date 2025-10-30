package com.redemonitor.main.util;

import java.time.Duration;
import java.time.Instant;

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

    public String createAccessToken(String username, String[] roles, int expireAt ) {
        Algorithm algorithm = Algorithm.HMAC256( secretKey );

        return JWT.create()
                .withIssuer( issuer )
                .withSubject( username )
                .withArrayClaim( "roles", roles )
                .withExpiresAt( Instant.now().plus( Duration.ofSeconds( expireAt ) ) )
                .sign( algorithm );
    }

    public String createRefreshToken( String username, int expireAt ) {
        Algorithm algorithm = Algorithm.HMAC256( secretKey );

        return JWT.create()
                .withIssuer( issuer )
                .withSubject( username )
                .withExpiresAt( Instant.now().plus( Duration.ofSeconds( expireAt ) ) )
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
