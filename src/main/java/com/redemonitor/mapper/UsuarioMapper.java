package com.redemonitor.mapper;

import com.redemonitor.dto.request.CreateUsuarioRequest;
import com.redemonitor.dto.request.UpdateUsuarioRequest;
import com.redemonitor.dto.response.UsuarioResponse;
import com.redemonitor.model.Usuario;
import org.springframework.stereotype.Component;

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
