package com.redemonitor.main.dto.integration.request;

import com.redemonitor.main.dto.integration.DispMonitorConfig;
import com.redemonitor.main.dto.integration.DispMonitorDispositivo;

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
public class DispMonitorStartMonitoramentoRequest {

	private DispMonitorDispositivo dispositivo;
	
	private DispMonitorConfig config;
	
}
