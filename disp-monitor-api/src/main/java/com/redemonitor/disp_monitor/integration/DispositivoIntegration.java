package com.redemonitor.disp_monitor.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.redemonitor.disp_monitor.integration.dto.request.SaveDispositivoStatusRequest;
import com.redemonitor.disp_monitor.integration.dto.response.DispositivoResponse;
import com.redemonitor.disp_monitor.mapper.DispositivoMapper;
import com.redemonitor.disp_monitor.model.Dispositivo;
import com.redemonitor.disp_monitor.util.HttpClientUtil;

@Component
public class DispositivoIntegration {

	@Value("${dispositivo.get.endpoint}") 
	private String dispositivoGetEndpoint;
	
	@Value("${dispositivo.update.status.endpoint}") 
	private String dispositivoUpdateStatusEndpoint;
		
	@Autowired
	private HttpClientUtil httpClientUtil;
	
	@Autowired
	private DispositivoMapper dispositivoMapper;
			
	public void saveDispositivo( Dispositivo dispositivo, String accessToken ) {
		String uri = dispositivoUpdateStatusEndpoint.replace( "{dispositivoId}", ""+dispositivo.getId() );
		
		SaveDispositivoStatusRequest request = dispositivoMapper.map( dispositivo );
		
		httpClientUtil.patch( uri, accessToken, request );					
	}
	
	public Dispositivo getDispositivo( Long dispositivoId, String accessToken ) {
		String uri = dispositivoGetEndpoint.replace( "{dispositivoId}", ""+dispositivoId ); 
		
		DispositivoResponse resp = httpClientUtil.get( uri, accessToken, DispositivoResponse.class );
		
		return dispositivoMapper.map( resp );
	}
	
}
