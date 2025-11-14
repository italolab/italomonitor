package com.italomonitor.main.dto.integration;

import com.italomonitor.main.enums.DispositivoStatus;

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
public class DispMonitorDispositivoState {

	private Long id;
			
	private DispositivoStatus status;
		
	private int latenciaMedia;
	
}
