package com.redemonitor.disp_monitor.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.redemonitor.disp_monitor.components.HttpClientManager;
import com.redemonitor.disp_monitor.dto.response.integration.DispositivoResponse;
import com.redemonitor.disp_monitor.mapper.DispositivoMapper;
import com.redemonitor.disp_monitor.model.Dispositivo;

@Component
public class DispositivoIntegration {
	
	@Value("${dispositivo.get.endpoint}") 
	private String dispositivoGetEndpoint;
			
	@Autowired
	private HttpClientManager httpClientManager;
	
	@Autowired
	private DispositivoMapper dispositivoMapper;
					
	public Dispositivo getDispositivo( Long dispositivoId ) {
		String uri = dispositivoGetEndpoint.replace( "{dispositivoId}", ""+dispositivoId ); 
		
		DispositivoResponse resp = httpClientManager.get( uri, DispositivoResponse.class );
		
		return dispositivoMapper.map( resp );
	}
	
}
