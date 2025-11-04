package com.redemonitor.main.components.util;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.redemonitor.main.exception.BusinessException;
import com.redemonitor.main.exception.Errors;

@Component
public class JwtTokenUtil {
	
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.issuer}")
    private String issuer;
    
    public String createAccessToken( String username, String[] roles, Long empresaId, String perfil, int expireAt ) {
        Algorithm algorithm = Algorithm.HMAC256( secretKey );

        return JWT.create()
                .withIssuer( issuer )
                .withSubject( username )
                .withArrayClaim( "roles", roles )
                .withClaim( "empresaId", empresaId )
                .withClaim( "perfil", perfil )
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
        
    public JWTInfos extractInfos( String authorizationHeader ) {
    	String accessToken = this.extractAccessToken( authorizationHeader );    	
    	return this.extractInfosByAccessToken( accessToken );
    }
    
    public JWTInfos extractInfosByAccessToken( String accessToken ) {
    	try {        	        	
            DecodedJWT decodedJWT = this.verifyToken( accessToken );
            String username =  decodedJWT.getSubject();
            Long empresaId = decodedJWT.getClaim( "empresaId" ).asLong();
            String perfil = decodedJWT.getClaim( "perfil" ).asString();
     
            return new JWTInfos( username, empresaId, perfil );
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

    public class JWTInfos {
    	
    	private String username;
    	private Long empresaId;
    	private String perfil;
    	
    	public JWTInfos( String username, Long empresaId, String perfil ) {
    		this.username = username;
    		this.empresaId = empresaId;
    		this.perfil = perfil;
    	}

		public String getUsername() {
			return username;
		}

		public Long getEmpresaId() {
			return empresaId;
		}

		public String getPerfil() {
			return perfil;
		}
    	
    }
    
}
