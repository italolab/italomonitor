package com.redemonitor.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.redemonitor.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${jwt.token.cookie.name}")
    private String tokenCookieName;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = null;

        Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( int i = 0; token == null && i < cookies.length; i++ )
                if ( cookies[i].getName().equals( tokenCookieName ) )
                    token = cookies[ i ].getValue();
        }

        if ( token != null ) {
            try {
                DecodedJWT decodedJWT = jwtTokenUtil.verifyToken( token );
                if ( decodedJWT.getExpiresAt().after( new Date() ) ) {
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim( "roles" ).asArray( String.class );

                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    for( String role : roles )
                        authorities.add( new SimpleGrantedAuthority( role ) );

                    UsernamePasswordAuthenticationToken userPassToken =
                            new UsernamePasswordAuthenticationToken( username, null, authorities );

                    SecurityContextHolder.getContext().setAuthentication( userPassToken );
                }
            } catch ( JWTVerificationException e ) {
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
