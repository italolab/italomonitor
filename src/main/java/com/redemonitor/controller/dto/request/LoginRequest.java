package com.redemonitor.controller.dto.request;

import com.redemonitor.controller.dto.validation.ValidationBuilder;
import com.redemonitor.controller.dto.validation.Validator;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {

    private String username;

    private String senha;

    public void validate() {
        List<Validator> validators = new ArrayList<>();

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
