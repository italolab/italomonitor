package com.italomonitor.disp_monitor.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.italomonitor.disp_monitor.components.util.BearerTokenUtil;
import com.italomonitor.disp_monitor.components.util.JwtTokenUtil;
import com.italomonitor.disp_monitor.dto.response.ErrorResponse;
import com.italomonitor.disp_monitor.exception.Errors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationFilter2 extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private BearerTokenUtil bearerTokenUtil;
    
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

    	String authorizationHeader = request.getHeader( "Authorization" );
    	
    	String accessToken = bearerTokenUtil.extractAccessToken( authorizationHeader );

        if ( accessToken != null ) {
            try {
                DecodedJWT decodedJWT = jwtTokenUtil.verifyToken( accessToken );
                if ( decodedJWT.getExpiresAt().after( new Date() ) ) {
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim( "roles" ).asArray( String.class );

                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    for( String role : roles ) {
                        authorities.add( new SimpleGrantedAuthority( role ) );
                    }

                    UsernamePasswordAuthenticationToken userPassToken =
                            new UsernamePasswordAuthenticationToken( username, null, authorities );

                    SecurityContextHolder.getContext().setAuthentication( userPassToken );
                }
            } catch ( JWTVerificationException e ) {
                ErrorResponse errResp = ErrorResponse.builder()
                        .message( Errors.INVALID_OR_EXPIRED_TOKEN )
                        .build();

                String resp = new ObjectMapper().writeValueAsString( errResp );

                response.setContentType( "application/json" );
                response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );

                PrintWriter writer = new PrintWriter( response.getOutputStream() );
                writer.print( resp );
                writer.flush();

                return;
            }
        }
        super.doFilter( request, response, filterChain );
    }

}
