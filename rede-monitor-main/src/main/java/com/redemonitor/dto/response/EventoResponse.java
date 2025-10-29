package com.redemonitor.dto.response;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventoResponse {

    private Long id;

    private int sucessosQuant;

    private int falhasQuant;

    private int quedasQuant;

    private int tempoInatividade;

    private int duracao;

    private Date criadoEm;

}
