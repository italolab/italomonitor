package com.redemonitor.main.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.redemonitor.main.components.HttpClientManager;
import com.redemonitor.main.integration.dto.ExisteNoMonitorResponse;
import com.redemonitor.main.integration.dto.InfoResponse;
import com.redemonitor.main.integration.dto.MonitoramentoOperResponse;

@Component
public class DispositivoMonitorIntegration {

	@Value("${monitoramento.dispositivo.start.endpoint.path}")
	private String startEndpointPath;
	
	@Value("${monitoramento.dispositivo.stop.endpoint.path}")
	private String stopEndpointPath;
	
	@Value("${monitoramento.dispositivo.update-config-in-monitores.endpoint.path}")
	private String updateConfigEndpointPath;
	
	@Value("${monitoramento.dispositivo.update-dispositivo-in-monitor.endpoint.path}")
	private String updateDispositivoEndpointPath;
	
	@Value("${monitoramento.dispositivo.info}")
	private String infoEndpointPath;
	
	@Value("${monitoramento.dispositivo.existe-no-monitor}")
	private String existeNoMonitorPath;
	
	@Autowired
	private HttpClientManager httpClientManager;
	
	public MonitoramentoOperResponse startMonitoramento( String serverHost, Long dispositivoId ) {
		String uri = serverHost + 
				startEndpointPath.replace( "{dispositivoId}", ""+dispositivoId ); 
		
		return httpClientManager.postWithResponse( uri, MonitoramentoOperResponse.class );
	}
	
	public MonitoramentoOperResponse stopMonitoramento( String serverHost, Long dispositivoId ) {
		String uri = serverHost + stopEndpointPath.replace( "{dispositivoId}", ""+dispositivoId );
		
		return httpClientManager.postWithResponse( uri, MonitoramentoOperResponse.class );
	}
	
	public MonitoramentoOperResponse updateConfigInMonitores( String serverHost ) {
		String uri = serverHost + updateConfigEndpointPath;
		
		return httpClientManager.postWithResponse( uri, MonitoramentoOperResponse.class );
	}
	
	public MonitoramentoOperResponse updateDispositivoInMonitor( String serverHost, Long dispositivoId ) {
		String uri = serverHost + updateDispositivoEndpointPath.replace( "{dispositivoId}", ""+dispositivoId );
		
		return httpClientManager.postWithResponse( uri, MonitoramentoOperResponse.class );				
	}
	
	public InfoResponse getInfo( String serverHost ) {
		String uri = serverHost + infoEndpointPath;
		
		return httpClientManager.get( uri, InfoResponse.class );
	}
	
	public ExisteNoMonitorResponse getExisteNoMonitor( String serverHost, Long dispositivoId ) {
		String uri = serverHost + existeNoMonitorPath.replace( "{dispositivoId}", ""+dispositivoId );
		
		return httpClientManager.get( uri, ExisteNoMonitorResponse.class );
	}
	
}
