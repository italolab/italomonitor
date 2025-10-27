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
public class SaveConfigRequest {

    private int numPacotesPorLote;
    private int monitoramentoDelay;
    private int registroEventoPeriodoSegundos;

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
                ValidationBuilder.of( "Período de registro de eventos", String.valueOf( registroEventoPeriodoSegundos ) )
                        .required()
                        .deveSerMaiorQueZero()
                        .build()
        );

        validators.forEach( Validator::validate );
    }

}
