package com.redemonitor.main.mapper;

import org.springframework.stereotype.Component;

import com.redemonitor.main.dto.request.CreateUsuarioRequest;
import com.redemonitor.main.dto.request.UpdateUsuarioRequest;
import com.redemonitor.main.dto.response.UsuarioResponse;
import com.redemonitor.main.model.Usuario;

@Component
public class UsuarioMapper {

    public Usuario map(CreateUsuarioRequest request ) {
        return Usuario.builder()
                .nome( request.getNome() )
                .email( request.getEmail() )
                .username( request.getUsername() )
                .senha( request.getSenha() )
                .build();
    }

    public UsuarioResponse map( Usuario usuario ) {
        return UsuarioResponse.builder()
                .id( usuario.getId() )
                .nome( usuario.getNome() )
                .username( usuario.getUsername() )
                .email( usuario.getEmail() )
                .build();
    }

    public void load( Usuario usuario, UpdateUsuarioRequest request ) {
        usuario.setNome( request.getNome() );
        usuario.setEmail( request.getEmail() );
        usuario.setUsername( request.getUsername() );
    }

}
