package italo.italomonitor.main.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import italo.italomonitor.main.validation.ValidationBuilder;
import italo.italomonitor.main.validation.Validator;

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
