package com.italomonitor.main.dto.request;

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
public class SaveDispositivoStateRequest {
	
    private DispositivoStatus status;
    
    private boolean sendoMonitorado;
    
    private int latenciaMedia;    

}
