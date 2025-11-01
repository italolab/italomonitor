package com.redemonitor.main.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.redemonitor.main.util.HttpClientUtil;

@Component
public class DispositivoMonitorIntegration {

	@Value("${monitoramento.dispositivo.start.endpoint}")
	private String startEndpoint;
	
	@Value("${monitoramento.dispositivo.stop.endpoint}")
	private String stopEndpoint;
	
	@Value("${monitoramento.dispositivo.update-config-in-monitores.endpoint}")
	private String updateConfigEndpoint;
	
	@Value("${monitoramento.dispositivo.update-dispositivo-in-monitor.endpoint}")
	private String updateDispositivoEndpoint;
	
	@Autowired
	private HttpClientUtil httpClientUtil;
	
	public void startMonitoramento( Long dispositivoId, String accessToken ) {
		String uri = startEndpoint.replace( "{dispositivoId}", ""+dispositivoId );
		
		httpClientUtil.post( uri, accessToken );
	}
	
	public void stopMonitoramento( Long dispositivoId, String accessToken ) {
		String uri = stopEndpoint.replace( "{dispositivoId}", ""+dispositivoId );
		
		httpClientUtil.post( uri, accessToken );
	}
	
	public void updateConfigInMonitores( String accessToken ) {
		String uri = updateConfigEndpoint;
		
		httpClientUtil.post( uri, accessToken );
	}
	
	public void updateDispositivoInMonitor( Long dispositivoId, String accessToken ) {
		String uri = updateDispositivoEndpoint.replace( "{dispositivoId}", ""+dispositivoId );
		
		httpClientUtil.post( uri, accessToken );				
	}
	
}
