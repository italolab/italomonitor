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
public class SaveConfigRequest {

    private int numPacotesPorLote;
    private int monitoramentoDelay;
    private int registroEventoPeriodo;

    public void validate() {
        List<Validator> validators = new ArrayList<>();

        validators.addAll(
                ValidationBuilder.of( "número de pacotes por lote", String.valueOf( numPacotesPorLote ) )
                        .required()
                        .deveSerMaiorQueZero()
                        .build()
        );

        validators.addAll(
                ValidationBuilder.of( "delay de monitoramento", String.valueOf( monitoramentoDelay ) )
                        .required()
                        .deveSerMaiorQueZero()
                        .build()
        );

        validators.addAll(
                ValidationBuilder.of( "Período de registro de eventos", String.valueOf(registroEventoPeriodo) )
                        .required()
                        .deveSerMaiorQueZero()
                        .build()
        );

        validators.forEach( Validator::validate );
    }

}
