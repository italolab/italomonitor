package italo.italomonitor.disp_monitor.service;

import java.lang.management.ManagementFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.management.OperatingSystemMXBean;

import italo.italomonitor.disp_monitor.dto.response.InfoResponse;

@Service
public class SistemaService {

	@Autowired
	private DispositivoMonitorService dispositivoMonitorService;
	
	public InfoResponse getInfo() {
		Runtime runtime = Runtime.getRuntime();
		
		OperatingSystemMXBean operSysMXBean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
		
		return InfoResponse.builder()
				.totalMemory( runtime.totalMemory() )
				.freeMemory( runtime.freeMemory() )
				.maxMemory( runtime.maxMemory() )	
				.usoCpu( operSysMXBean.getProcessCpuLoad() ) 
				.availableProcessors( runtime.availableProcessors() ) 
				.numThreadsAtivas( dispositivoMonitorService.getNumThreadsAtivas() ) 
				.build();
	}
	
}
