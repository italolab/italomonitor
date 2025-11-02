package com.redemonitor.main.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.redemonitor.main.integration.dto.ExisteNoMonitorResponse;
import com.redemonitor.main.integration.dto.InfoResponse;
import com.redemonitor.main.integration.dto.MonitoramentoOperResponse;
import com.redemonitor.main.util.HttpClientUtil;

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
	private HttpClientUtil httpClientUtil;
	
	public MonitoramentoOperResponse startMonitoramento( String serverHost, Long dispositivoId, String accessToken ) {
		String uri = serverHost + startEndpointPath.replace( "{dispositivoId}", ""+dispositivoId );
		
		return httpClientUtil.postWithResponse( uri, accessToken, MonitoramentoOperResponse.class );
	}
	
	public MonitoramentoOperResponse stopMonitoramento( String serverHost, Long dispositivoId, String accessToken ) {
		String uri = serverHost + stopEndpointPath.replace( "{dispositivoId}", ""+dispositivoId );
		
		return httpClientUtil.postWithResponse( uri, accessToken, MonitoramentoOperResponse.class );
	}
	
	public MonitoramentoOperResponse updateConfigInMonitores( String serverHost, String accessToken ) {
		String uri = serverHost + updateConfigEndpointPath;
		
		return httpClientUtil.postWithResponse( uri, accessToken, MonitoramentoOperResponse.class );
	}
	
	public MonitoramentoOperResponse updateDispositivoInMonitor( String serverHost, Long dispositivoId, String accessToken ) {
		String uri = serverHost + updateDispositivoEndpointPath.replace( "{dispositivoId}", ""+dispositivoId );
		
		return httpClientUtil.postWithResponse( uri, accessToken, MonitoramentoOperResponse.class );				
	}
	
	public InfoResponse getInfo( String serverHost, String accessToken ) {
		String uri = serverHost + infoEndpointPath;
		
		return httpClientUtil.get( uri, accessToken, InfoResponse.class );
	}
	
	public ExisteNoMonitorResponse getExisteNoMonitor( String serverHost, Long dispositivoId, String accessToken ) {
		String uri = serverHost + existeNoMonitorPath.replace( "{dispositivoId}", ""+dispositivoId );
		
		return httpClientUtil.get( uri, accessToken, ExisteNoMonitorResponse.class );
	}
	
}
