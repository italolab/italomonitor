package com.redemonitor.main.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.redemonitor.main.components.HttpClientManager;
import com.redemonitor.main.dto.integration.DispMonitorConfig;
import com.redemonitor.main.dto.integration.DispMonitorDispositivo;
import com.redemonitor.main.dto.integration.request.DispMonitorStartMonitoramentoRequest;
import com.redemonitor.main.dto.response.integration.ExisteNoMonitorResponse;
import com.redemonitor.main.dto.response.integration.InfoResponse;
import com.redemonitor.main.dto.response.integration.MonitoramentoOperResponse;
import com.redemonitor.main.mapper.ConfigMapper;
import com.redemonitor.main.mapper.DispositivoMapper;
import com.redemonitor.main.model.Config;
import com.redemonitor.main.model.Dispositivo;

@Component
public class DispositivoMonitorIntegration {

	@Value("${monitoramento.dispositivo.start.endpoint.path}")
	private String startEndpointPath;
	
	@Value("${monitoramento.dispositivo.stop.endpoint.path}")
	private String stopEndpointPath;
		
	@Value("${monitoramento.dispositivo.stop-all.endpoint.path}")
	private String stopAllEndpointPath;
	
	@Value("${monitoramento.dispositivo.info}")
	private String infoEndpointPath;
	
	@Value("${monitoramento.dispositivo.existe-no-monitor}")
	private String existeNoMonitorPath;
	
	@Autowired
	private DispositivoMapper dispositivoMapper;
	
	@Autowired
	private ConfigMapper configMapper;
	
	@Autowired
	private HttpClientManager httpClientManager;
	
	public MonitoramentoOperResponse startMonitoramento( String serverHost, Dispositivo dispositivo, Config config ) {					
		DispMonitorDispositivo dmDisp = dispositivoMapper.mapToDispMonitorDispositivo( dispositivo ); 		
		DispMonitorConfig dmConfig = configMapper.mapToDispMonitorConfig( config );
		
		DispMonitorStartMonitoramentoRequest request = DispMonitorStartMonitoramentoRequest.builder()
				.dispositivo( dmDisp )
				.config( dmConfig )
				.build();
		
		String uri = serverHost + startEndpointPath;
		
		return httpClientManager.postWithResponse( uri, request, MonitoramentoOperResponse.class );
	}
	
	public MonitoramentoOperResponse stopMonitoramento( String serverHost, Long dispositivoId ) {
		String uri = serverHost + stopEndpointPath.replace( "{dispositivoId}", ""+dispositivoId );
				
		return httpClientManager.postWithResponse( uri, MonitoramentoOperResponse.class );
	}
	
	public void stopAllMonitoramentos( String serverHost ) {
		String uri = serverHost + stopAllEndpointPath;
		
		httpClientManager.post( uri ); 
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
