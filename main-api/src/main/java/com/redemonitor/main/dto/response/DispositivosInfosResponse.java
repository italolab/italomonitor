package com.redemonitor.main.dto.response;

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
public class DispositivosInfosResponse {

	private int sendoMonitoradosQuant;
	
	private int quantTotal;
	
}
