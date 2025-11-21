package italo.italomonitor.main.dto.request;

import java.util.ArrayList;
import java.util.List;

import italo.italomonitor.main.validation.ValidationBuilder;
import italo.italomonitor.main.validation.Validator;
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
public class SaveAgenteRequest {

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
