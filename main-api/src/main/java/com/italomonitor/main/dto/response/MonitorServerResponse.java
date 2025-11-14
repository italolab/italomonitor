package com.italomonitor.main.dto.response;

import com.italomonitor.main.dto.integration.response.MonitorServerInfoResponse;

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
public class MonitorServerResponse {

	private Long id;
	
	private String host;
	
	private boolean ativo;

	private MonitorServerInfoResponse info;
		
}
