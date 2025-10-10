package com.redemonitor.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.redemonitor.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AuthorizationFilter2 extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String auth = request.getHeader( "Authorization" );

        boolean authHeaderValido = true;
        if ( auth == null ) {
            authHeaderValido = false;
        } else {
            authHeaderValido = auth.startsWith( "Bearer " );
        }

        if ( authHeaderValido && auth != null ) {
            String token = auth.substring( 7 );
            try {
                DecodedJWT decodedJWT = jwtTokenUtil.verifyToken( token );
                if ( decodedJWT.getExpiresAt().after( new Date() ) ) {
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim( "roles" ).asArray( String.class );

                    for( String role : roles )
                        System.out.println( role );

                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    for( String role : roles )
                        authorities.add( new SimpleGrantedAuthority( role ) );

                    UsernamePasswordAuthenticationToken userPassToken =
                            new UsernamePasswordAuthenticationToken( username, null, authorities );

                    SecurityContextHolder.getContext().setAuthentication( userPassToken );
                }
            } catch ( JWTVerificationException e ) {
                e.printStackTrace();
                String resp = "{ \"message\" : \"Token inválido ou expirado. Por favor faça login novamente.\" }";
                response.setContentType( "application/json" );
                response.setStatus( HttpServletResponse.SC_BAD_REQUEST );

                PrintWriter writer = new PrintWriter( response.getOutputStream() );
                writer.print( resp );
                writer.flush();

                return;
            }
        }
        super.doFilter( request, response, filterChain );
    }

}
