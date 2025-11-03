package com.redemonitor.disp_monitor.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.redemonitor.disp_monitor.components.HttpClientUtil;
import com.redemonitor.disp_monitor.integration.dto.request.SaveEventoRequest;
import com.redemonitor.disp_monitor.mapper.EventoMapper;
import com.redemonitor.disp_monitor.model.Evento;

@Component
public class EventoIntegration {

	@Value("${evento.create.endpoint}") 
	private String eventoCreateEndpoint;
		
	@Autowired
	private HttpClientUtil httpClientUtil;

	@Autowired
	private EventoMapper eventoMapper;
	
	public void saveEvento( Evento evento, String accessToken ) {
		String uri = eventoCreateEndpoint;
		
		SaveEventoRequest request = eventoMapper.map( evento );
		
		httpClientUtil.post( uri, accessToken, request );			
	}
	
}
