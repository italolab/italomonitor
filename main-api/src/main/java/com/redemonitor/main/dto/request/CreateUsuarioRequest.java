package com.redemonitor.main.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.redemonitor.main.validation.ValidationBuilder;
import com.redemonitor.main.validation.Validator;

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
