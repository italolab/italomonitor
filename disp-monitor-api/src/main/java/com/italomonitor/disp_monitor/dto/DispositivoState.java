package com.italomonitor.disp_monitor.dto;

import com.italomonitor.disp_monitor.enums.DispositivoStatus;

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
public class DispositivoState {
		
	private Long id;
	
	private DispositivoStatus status;
	
	private int latenciaMedia;
	
}
