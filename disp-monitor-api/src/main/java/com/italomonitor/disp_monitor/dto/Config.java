package com.italomonitor.disp_monitor.dto;

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
public class Config {

	private int monitoramentoDelay;

    private int numPacotesPorLote;

    private int registroEventoPeriodo;
    
    private int numThreadsLimite;
	
}
