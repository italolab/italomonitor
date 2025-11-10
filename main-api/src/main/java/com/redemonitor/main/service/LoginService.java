package com.redemonitor.main.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.redemonitor.main.components.util.DateUtil;
import com.redemonitor.main.components.util.HashUtil;
import com.redemonitor.main.components.util.JwtTokenUtil;
import com.redemonitor.main.components.util.JwtTokenUtil.JWTInfos;
import com.redemonitor.main.dto.request.LoginRequest;
import com.redemonitor.main.dto.response.LoginResponse;
import com.redemonitor.main.enums.UsuarioPerfil;
import com.redemonitor.main.exception.BusinessException;
import com.redemonitor.main.exception.Errors;
import com.redemonitor.main.model.Empresa;
import com.redemonitor.main.model.Role;
import com.redemonitor.main.model.RoleGrupoMap;
import com.redemonitor.main.model.Usuario;
import com.redemonitor.main.model.UsuarioGrupo;
import com.redemonitor.main.model.UsuarioGrupoMap;
import com.redemonitor.main.repository.UsuarioRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HashUtil hashUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private DateUtil dateUtil;

    @Value("${jwt.access_token.cookie.name}")
    private String accessTokenCookieName;

    @Value("${jwt.refresh_token.cookie.name}")
    private String refreshTokenCookieName;

    @Value("${jwt.access_token.expire.at}")
    private int accessTokenExpireAt;

    @Value("${jwt.refresh_token.expire.at}")
    private int refreshTokenExpireAt;
    
    @Value("${grupo.admin}")
    private String grupoAdmin;       

    @Transactional(readOnly=true)
    public LoginResponse login(LoginRequest request, HttpServletResponse httpResponse) {
        request.validate();

        String username = request.getUsername();
        String senha = hashUtil.geraHash( request.getSenha() );

        Optional<Usuario> usuarioOp = usuarioRepository.findByLogin( username, senha );
        if ( usuarioOp.isEmpty() )
            throw new BusinessException( Errors.USER_NOT_FOUND );

        Usuario usuario = usuarioOp.get();
        String nome = usuario.getNome();
        
        Long empresaId = -1L;
        
        Empresa empresa = usuario.getEmpresa();
        if ( empresa != null )
        	empresaId = empresa.getId();
        
        if ( usuario.getPerfil() != UsuarioPerfil.ADMIN && empresa != null ) {
        	if ( empresa.isTemporario() ) {
        		LocalDateTime criadoEm = dateUtil.dateToLocalDateTime( empresa.getCriadoEm() );
        		LocalDateTime criadoEmAdded = criadoEm.plusDays( empresa.getUsoTemporarioPor() );
        		if ( LocalDateTime.now().isAfter( criadoEmAdded ) ) 
        			throw new BusinessException( Errors.TIME_OF_TEST_EXPIRED );        	
        	}
        }

        String accessToken = this.generateNewAccessToken( usuario, empresaId, accessTokenExpireAt );
        String refreshToken = this.generateNewRefreshToken( usuario, refreshTokenExpireAt );

        httpResponse.addCookie( this.buildAccessTokenCookie( accessToken, accessTokenExpireAt ) );
        httpResponse.addCookie( this.buildRefreshTokenCookie( refreshToken, refreshTokenExpireAt ) );

        JWTInfos jwtInfos = jwtTokenUtil.extractInfosByAccessToken( accessToken );

        return LoginResponse.builder()
                .nome( nome )
                .username( username )
                .accessToken( accessToken )
                .empresaId( jwtInfos.getEmpresaId() )
                .perfil( jwtInfos.getPerfil() ) 
                .build();
    }

    @Transactional(readOnly=true)
    public LoginResponse generateNewAccessToken( HttpServletResponse httpResponse, String refreshToken ) {
        try {
            DecodedJWT decodedJWT = jwtTokenUtil.verifyToken( refreshToken );
            String username = decodedJWT.getSubject();

            Optional<Usuario> usuarioOp = usuarioRepository.findByUsername( username );
            if ( usuarioOp.isEmpty() )
                throw new BusinessException( Errors.USER_NOT_FOUND );

            Usuario usuario = usuarioOp.get();
            String nome = usuario.getNome();
            
            Long empresaId = -1L;
            
            Empresa empresa = usuario.getEmpresa();
            if ( empresa != null )
            	empresaId = empresa.getId();            

            String accessToken = this.generateNewAccessToken( usuario, empresaId, accessTokenExpireAt );

            httpResponse.addCookie( this.buildAccessTokenCookie( accessToken, accessTokenExpireAt ) );

            JWTInfos jwtInfos = jwtTokenUtil.extractInfosByAccessToken( accessToken );
            
            return LoginResponse.builder()
                    .nome( nome )
                    .username( username )
                    .accessToken( accessToken )
                    .empresaId( jwtInfos.getEmpresaId() )
                    .perfil( jwtInfos.getPerfil() ) 
                    .build();
        } catch ( JWTVerificationException e ) {
            throw new BusinessException( Errors.NOT_AUTHORIZED );
        }
    }

    public void logout( HttpServletResponse httpResponse ) {
        httpResponse.addCookie( this.buildAccessTokenCookie( "", 0 ) );
        httpResponse.addCookie( this.buildRefreshTokenCookie( "", 0 ) );
    }

    private String generateNewAccessToken( Usuario usuario, Long empresaId, int expireAt ) {
        String username = usuario.getUsername();
        String perfil = usuario.getPerfil().name();
       
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
                
        return jwtTokenUtil.createAccessToken( username, rolesArray, empresaId, perfil, expireAt );
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
