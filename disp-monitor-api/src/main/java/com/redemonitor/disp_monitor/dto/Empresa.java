package com.redemonitor.disp_monitor.dto;

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
public class Empresa {

	private Long id;
        
    private double porcentagemMaxFalhasPorLote;
	
}
