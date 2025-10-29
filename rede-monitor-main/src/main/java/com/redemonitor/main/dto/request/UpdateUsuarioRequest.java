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
public class UpdateUsuarioRequest {

    private String nome;

    private String email;

    private String username;

    // Pode ser -1 para nenhuma empresa
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

        validators.forEach( Validator::validate );
    }


}
