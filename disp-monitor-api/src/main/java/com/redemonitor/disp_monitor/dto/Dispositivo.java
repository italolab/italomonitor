package com.redemonitor.disp_monitor.dto;

import java.util.Date;

import com.redemonitor.disp_monitor.enums.DispositivoStatus;

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
public class Dispositivo {

	private Long id;
	
    private String host;
    
    private String nome;
    
    private String descricao;
    
    private String localizacao;
    
    private boolean sendoMonitorado;
    
    private DispositivoStatus status;
    
    private int latenciaMedia;
    
    private Date stateAtualizadoEm;
    
    private Empresa empresa;
	
}
