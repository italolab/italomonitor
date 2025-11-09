package com.redemonitor.disp_monitor.dto;

import java.util.Date;

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
public class Evento {

    private int sucessosQuant;

    private int falhasQuant;

    private int quedasQuant;

    private int tempoInatividade;

    private int duracao;

    private Date criadoEm;

    private Long dispositivoId;
	
}
