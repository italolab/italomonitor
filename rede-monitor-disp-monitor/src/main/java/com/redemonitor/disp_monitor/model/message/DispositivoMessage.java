package com.redemonitor.disp_monitor.model.message;

import com.redemonitor.disp_monitor.model.enums.DispositivoStatus;

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
