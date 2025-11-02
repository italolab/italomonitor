package com.redemonitor.disp_monitor.dto;

import com.redemonitor.disp_monitor.enums.StartMonitoramentoStatus;

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
public class StartMonitoramentoResponse {

	private StartMonitoramentoStatus status;
	
	private int numThreadsAtivas;
	
}
