package com.redemonitor.disp_monitor.dto.message;

import com.redemonitor.disp_monitor.enums.DispositivoStatus;

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
public class DispositivoMessage {
		
	private Long id;
	
	private DispositivoStatus status;
	
	private boolean sendoMonitorado;
	
	private String username;
	
}
