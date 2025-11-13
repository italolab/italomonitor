package com.redemonitor.main.service;

import org.springframework.stereotype.Service;

import com.redemonitor.main.dto.response.InfoResponse;

@Service
public class SistemaService {
	
	public InfoResponse getInfo() {				
		Runtime runtime = Runtime.getRuntime();
		return InfoResponse.builder()
				.totalMemory( runtime.totalMemory() )
				.freeMemory( runtime.freeMemory() )
				.maxMemory( runtime.maxMemory() )	
				.availableProcessors( runtime.availableProcessors() ) 
				.build();
	}
	
}
