package com.redemonitor.dto.request;

import com.redemonitor.dto.validation.ValidationBuilder;
import com.redemonitor.dto.validation.Validator;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
