package com.redemonitor.dto.request;

import com.redemonitor.validation.ValidationBuilder;
import com.redemonitor.validation.Validator;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
