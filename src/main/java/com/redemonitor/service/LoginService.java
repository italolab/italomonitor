package com.redemonitor.service;

import com.redemonitor.controller.dto.request.LoginRequest;
import com.redemonitor.controller.dto.response.LoginResponse;
import com.redemonitor.exception.ErrorException;
import com.redemonitor.exception.Errors;
import com.redemonitor.model.*;
import com.redemonitor.repository.UsuarioRepository;
import com.redemonitor.util.HashUtil;
import com.redemonitor.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    public LoginResponse login( LoginRequest request ) {
        String username = request.getUsername();
        String senha = hashUtil.geraHash( request.getSenha() );

        Optional<Usuario> usuarioOp = usuarioRepository.findByLogin( username, senha );
        if ( usuarioOp.isEmpty() )
            throw new ErrorException( Errors.USER_NOT_FOUND );

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

        return LoginResponse.builder()
                .nome( nome )
                .username( username )
                .token( token )
                .build();
    }

}
