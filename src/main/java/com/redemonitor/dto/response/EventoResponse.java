package com.redemonitor.dto.response;

import lombok.*;

import java.time.LocalDateTime;

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

    private LocalDateTime criadoEm;

}
