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
public class SaveEmpresaRequest {

    private String nome;
    private String emailNotif;
    private double porcentagemMaxFalhasPorLote;

    public void validate() {
        List<Validator> validators = new ArrayList<>();

        validators.addAll(
                ValidationBuilder.of( "nome", nome )
                        .required()
                        .build()
        );

        validators.addAll(
                ValidationBuilder.of( "email de notificação", emailNotif )
                        .email()
                        .build()
        );

        validators.addAll(
                ValidationBuilder.of( "porcentagem máxima de falhas por lote", String.valueOf( porcentagemMaxFalhasPorLote ) )
                        .required()
                        .deveSerMaiorQueZero()
                        .build()
        );

        validators.forEach( Validator::validate );
    }

}
