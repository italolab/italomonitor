package com.redemonitor.disp_monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redemonitor.disp_monitor.dto.response.InfoResponse;

@Service
public class SistemaService {

	@Autowired
	private DispositivoMonitorService dispositivoMonitorService;
	
	public InfoResponse getInfo() {
		Runtime runtime = Runtime.getRuntime();
		
		return InfoResponse.builder()
				.totalMemory( runtime.totalMemory() )
				.freeMemory( runtime.freeMemory() )
				.maxMemory( runtime.maxMemory() )	
				.availableProcessors( runtime.availableProcessors() ) 
				.numThreadsAtivas( dispositivoMonitorService.getNumThreadsAtivas() ) 
				.build();
	}
	
}
