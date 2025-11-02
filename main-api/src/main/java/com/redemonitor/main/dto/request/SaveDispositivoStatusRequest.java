package com.redemonitor.main.dto.request;

import com.redemonitor.main.enums.DispositivoStatus;

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
public class SaveDispositivoStatusRequest {

	private boolean sendoMonitorado;
	
    private DispositivoStatus status;

}
