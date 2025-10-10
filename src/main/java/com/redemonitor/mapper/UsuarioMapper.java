package com.redemonitor.mapper;

import com.redemonitor.controller.dto.request.UsuarioRequest;
import com.redemonitor.model.Usuario;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario map(UsuarioRequest request ) {
        return Usuario.builder()
                .nome( request.getNome() )
                .email( request.getEmail() )
                .username( request.getUsername() )
                .senha( request.getSenha() )
                .build();
    }

}
