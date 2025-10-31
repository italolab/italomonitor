package com.redemonitor.disp_monitor.model;

import com.redemonitor.disp_monitor.integration.dto.enums.DispositivoStatus;

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
    private Empresa empresa;
	
}
