package com.redemonitor.service;

import com.redemonitor.dto.request.LoginRequest;
import com.redemonitor.dto.response.LoginResponse;
import com.redemonitor.exception.BusinessException;
import com.redemonitor.exception.Errors;
import com.redemonitor.model.*;
import com.redemonitor.repository.UsuarioRepository;
import com.redemonitor.util.HashUtil;
import com.redemonitor.util.JwtTokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
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

    @Value("${jwt.token.cookie.name}")
    private String tokenCookieName;

    public LoginResponse login(LoginRequest request, HttpServletResponse httpResponse) {
        request.validate();

        String username = request.getUsername();
        String senha = hashUtil.geraHash( request.getSenha() );

        Optional<Usuario> usuarioOp = usuarioRepository.findByLogin( username, senha );
        if ( usuarioOp.isEmpty() )
            throw new BusinessException( Errors.USER_NOT_FOUND );

        List<String> roles = new ArrayList<>();

        Usuario usuario = usuarioOp.get();
        String nome = usuario.getNome();

        username = usuario.getUsername();

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

        String token = jwtTokenUtil.createToken( username, rolesArray );

        Cookie cookie = new Cookie( tokenCookieName, token );
        cookie.setHttpOnly( true );
        cookie.setSecure( true );
        cookie.setMaxAge( 60 * 60 * 24 * 7 );

        httpResponse.addCookie( cookie );

        return LoginResponse.builder()
                .nome( nome )
                .username( username )
                .build();
    }

}
