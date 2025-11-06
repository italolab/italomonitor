package com.redemonitor.disp_monitor.dto.response;

import com.redemonitor.disp_monitor.enums.MonitoramentoOperResult;

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
public class MonitoramentoOperResponse {

	private MonitoramentoOperResult result;
		
}
