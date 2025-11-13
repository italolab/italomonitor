package com.redemonitor.main.dto.integration.response;

import com.redemonitor.main.enums.MonitoramentoOperResult;

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
