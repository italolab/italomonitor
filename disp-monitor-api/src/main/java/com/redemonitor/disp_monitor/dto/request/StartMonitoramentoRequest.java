package com.redemonitor.disp_monitor.dto.request;

import com.redemonitor.disp_monitor.model.Config;
import com.redemonitor.disp_monitor.model.Dispositivo;

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
public class StartMonitoramentoRequest {

	private Dispositivo dispositivo;
	
	private Config config;
	
}
