package italo.italomonitor.main.service;

import java.lang.management.ManagementFactory;

import org.springframework.stereotype.Service;

import com.sun.management.OperatingSystemMXBean;

import italo.italomonitor.main.dto.response.InfoResponse;

@Service
public class SistemaService {
	
	public InfoResponse getInfo() {				
		Runtime runtime = Runtime.getRuntime();
		OperatingSystemMXBean operSysMXBean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
				
		return InfoResponse.builder()
				.totalMemory( runtime.totalMemory() )
				.freeMemory( runtime.freeMemory() )
				.maxMemory( runtime.maxMemory() )	
				.usoCpu( operSysMXBean.getProcessCpuLoad() ) 
				.availableProcessors( runtime.availableProcessors() ) 
				.build();			
	}
	
}
