package italo.italomonitor.disp_monitor.dto.request;

import italo.italomonitor.disp_monitor.lib.to.Config;
import italo.italomonitor.disp_monitor.lib.to.Dispositivo;
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
public class StartMonitoramentoRequest {

	private Dispositivo dispositivo; // presente na library disp-monitor-lib
	
	private Config config;           // presente na library disp-monitor-lib
	
}
