package com.italomonitor.main.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.italomonitor.main.validation.ValidationBuilder;
import com.italomonitor.main.validation.Validator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SaveDispositivoRequest {

    private String host;
    private String nome;
    private String descricao;
    private String localizacao;
    private Long empresaId;

    public void validate() {
        List<Validator> validators = new ArrayList<>();

        validators.addAll(
                ValidationBuilder.of( "host", host )
                        .required()
                        .build()
        );

        validators.addAll(
                ValidationBuilder.of( "nome", nome )
                        .required()
                        .build()
        );

        validators.addAll(
                ValidationBuilder.of( "descricao", descricao )
                        .build()
        );

        validators.addAll(
                ValidationBuilder.of( "localizacao", localizacao )
                        .required()
                        .build()
        );

        validators.forEach( Validator::validate );
    }

}
