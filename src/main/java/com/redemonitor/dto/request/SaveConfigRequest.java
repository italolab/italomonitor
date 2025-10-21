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

    private int maxFalhasConsecutivas;
    private int numPacotesPorVez;
    private int monitoramentoDelay;

    public void validate() {
        List<Validator> validators = new ArrayList<>();

        validators.addAll(
                ValidationBuilder.of( "máximo falhas consecutivas", String.valueOf( maxFalhasConsecutivas ) )
                        .deveSerMaiorQueZero()
                        .build()
        );

        validators.addAll(
                ValidationBuilder.of( "número de pacotes por vez", String.valueOf( numPacotesPorVez ) )
                        .deveSerMaiorQueZero()
                        .build()
        );


        validators.addAll(
                ValidationBuilder.of( "delay de monitoramento", String.valueOf( monitoramentoDelay ) )
                        .deveSerMaiorQueZero()
                        .build()
        );

        validators.forEach( Validator::validate );
    }

}
