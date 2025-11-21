package italo.italomonitor.main.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import italo.italomonitor.main.components.util.JwtTokenUtil;
import italo.italomonitor.main.dto.response.ErrorResponse;
import italo.italomonitor.main.exception.Errors;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationFilter2 extends OncePerRequestFilter {

    @Value("${jwt.access_token.cookie.name}")
    private String accessTokenCookieName;

    @Value("${login.endpoint}")
    private String loginEndpoint;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
        
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

    	MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest( request );
    	
        String accessToken = null;

        if ( mutableRequest.getRequestURI().equals( loginEndpoint ) ) {
            Cookie cookie = new Cookie( accessTokenCookieName, "" );
            cookie.setMaxAge( 0 );
            cookie.setHttpOnly( true );
            response.addCookie( cookie );
        } else {
            Cookie[] cookies = mutableRequest.getCookies();
            if ( cookies != null ) {
                for ( int i = 0; accessToken == null && i < cookies.length; i++ ) {
                    if ( cookies[ i ].getName().equals( accessTokenCookieName ) ) {
                        accessToken = cookies[ i ].getValue();
                        mutableRequest.setAuthorizationHeader( "Bearer "+accessToken );
                    }
                }
            }
        }
        
        if ( accessToken == null ) {
        	String authorizationHeader = mutableRequest.getHeader( "Authorization" );
        	accessToken = jwtTokenUtil.extractAccessToken( authorizationHeader );        	
        }

        if ( accessToken != null ) {
            try {
                DecodedJWT decodedJWT = jwtTokenUtil.verifyToken( accessToken );
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
        super.doFilter( mutableRequest, response, filterChain );
    }

}
