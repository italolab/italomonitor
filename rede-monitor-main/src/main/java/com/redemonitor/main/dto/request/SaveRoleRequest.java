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
public class SaveRoleRequest {

    private String nome;

    public void validate() {
        List<Validator> validators = new ArrayList<>();

        validators.addAll(
                ValidationBuilder.of( "nome", nome )
                        .required()
                        .build()
        );

        validators.forEach( Validator::validate );
    }

}
