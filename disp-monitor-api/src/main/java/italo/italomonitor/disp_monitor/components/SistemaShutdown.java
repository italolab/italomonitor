package italo.italomonitor.disp_monitor.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

@Component
public class SistemaShutdown {

	@Autowired
	private ThreadPoolTaskScheduler scheduler;
	
	@PreDestroy
	public void cleanup() {
		if ( scheduler.isRunning() )
			scheduler.shutdown();
	}
	
}
