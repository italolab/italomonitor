package com.italomonitor.main.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.italomonitor.main.enums.UsuarioPerfil;
import com.italomonitor.main.validation.ValidationBuilder;
import com.italomonitor.main.validation.Validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateUsuarioRequest {

    private String nome;

    private String email;

    private String username;

    private String senha;
    
    private UsuarioPerfil perfil;

    private Long empresaId;

    public void validate() {
        List<Validator> validators = new ArrayList<>();

        validators.addAll(
                ValidationBuilder.of( "nome", nome )
                        .required()
                        .build()
        );

        validators.addAll(
                ValidationBuilder.of( "e-mail", email )
                        .required()
                        .email()
                        .build()
        );

        validators.addAll(
                ValidationBuilder.of( "username", username )
                        .required()
                        .build()
        );

        validators.addAll(
                ValidationBuilder.of( "senha", senha )
                        .required()
                        .build()
        );

        validators.forEach( Validator::validate );
    }

}
