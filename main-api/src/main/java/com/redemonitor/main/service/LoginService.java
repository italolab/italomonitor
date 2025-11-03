package com.redemonitor.main.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.redemonitor.main.components.util.HashUtil;
import com.redemonitor.main.components.util.JwtTokenUtil;
import com.redemonitor.main.dto.request.LoginRequest;
import com.redemonitor.main.dto.response.LoginResponse;
import com.redemonitor.main.exception.BusinessException;
import com.redemonitor.main.exception.Errors;
import com.redemonitor.main.model.*;
import com.redemonitor.main.repository.UsuarioRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HashUtil hashUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.access_token.cookie.name}")
    private String accessTokenCookieName;

    @Value("${jwt.refresh_token.cookie.name}")
    private String refreshTokenCookieName;

    @Value("${jwt.access_token.expire.at}")
    private int accessTokenExpireAt;

    @Value("${jwt.refresh_token.expire.at}")
    private int refreshTokenExpireAt;

    @Transactional
    public LoginResponse login(LoginRequest request, HttpServletResponse httpResponse) {
        request.validate();

        String username = request.getUsername();
        String senha = hashUtil.geraHash( request.getSenha() );

        Optional<Usuario> usuarioOp = usuarioRepository.findByLogin( username, senha );
        if ( usuarioOp.isEmpty() )
            throw new BusinessException( Errors.USER_NOT_FOUND );

        Usuario usuario = usuarioOp.get();
        String nome = usuario.getNome();

        String accessToken = this.generateNewAccessToken( usuario, accessTokenExpireAt );
        String refreshToken = this.generateNewRefreshToken( usuario, refreshTokenExpireAt );

        httpResponse.addCookie( this.buildAccessTokenCookie( accessToken, accessTokenExpireAt ) );
        httpResponse.addCookie( this.buildRefreshTokenCookie( refreshToken, refreshTokenExpireAt ) );

        return LoginResponse.builder()
                .nome( nome )
                .username( username )
                .accessToken( accessToken )
                .build();
    }

    @Transactional
    public LoginResponse generateNewAccessToken( HttpServletResponse httpResponse, String refreshToken ) {
        try {
            DecodedJWT decodedJWT = jwtTokenUtil.verifyToken( refreshToken );
            String username = decodedJWT.getSubject();

            Optional<Usuario> usuarioOp = usuarioRepository.findByUsername( username );
            if ( usuarioOp.isEmpty() )
                throw new BusinessException( Errors.USER_NOT_FOUND );

            Usuario usuario = usuarioOp.get();
            String nome = usuario.getNome();

            String accessToken = this.generateNewAccessToken( usuario, accessTokenExpireAt );

            httpResponse.addCookie( this.buildAccessTokenCookie( accessToken, accessTokenExpireAt ) );

            return LoginResponse.builder()
                    .nome( nome )
                    .username( username )
                    .accessToken( accessToken )
                    .build();
        } catch ( JWTVerificationException e ) {
            throw new BusinessException( Errors.NOT_AUTHORIZED );
        }
    }

    public void logout( HttpServletResponse httpResponse ) {
        httpResponse.addCookie( this.buildAccessTokenCookie( "", 0 ) );
        httpResponse.addCookie( this.buildRefreshTokenCookie( "", 0 ) );
    }

    private String generateNewAccessToken( Usuario usuario, int expireAt ) {
        String username = usuario.getUsername();

        List<String> roles = new ArrayList<>();

        for( UsuarioGrupoMap gruposMaps : usuario.getGrupos() ) {
            UsuarioGrupo grupo = gruposMaps.getUsuarioGrupo();
            for( RoleGrupoMap rolesGrupos : grupo.getRoles() ) {
                Role role = rolesGrupos.getRole();
                String roleNome = role.getNome();
                if ( !roles.contains( roleNome ) )
                    roles.add( roleNome );
            }
        }

        String[] rolesArray = new String[ roles.size() ];
        rolesArray = roles.toArray( rolesArray );

        return jwtTokenUtil.createAccessToken( username, rolesArray, expireAt );
    }

    private String generateNewRefreshToken( Usuario usuario, int expireAt ) {
        String username = usuario.getUsername();
        return jwtTokenUtil.createRefreshToken( username, expireAt );
    }

    private Cookie buildAccessTokenCookie( String accessToken, int expireAt ) {
        Cookie accessTokenCookie = new Cookie( accessTokenCookieName, accessToken );
        accessTokenCookie.setHttpOnly( true );
        accessTokenCookie.setSecure( true );
        accessTokenCookie.setMaxAge( expireAt );
        accessTokenCookie.setPath( "/" );
        return accessTokenCookie;
    }

    private Cookie buildRefreshTokenCookie( String refreshToken, int expireAt ) {
        Cookie refreshTokenCookie = new Cookie( refreshTokenCookieName, refreshToken );
        refreshTokenCookie.setHttpOnly( true );
        refreshTokenCookie.setSecure( true );
        refreshTokenCookie.setMaxAge( expireAt );
        refreshTokenCookie.setPath( "/" );
        return refreshTokenCookie;
    }

}
