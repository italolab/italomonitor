package com.redemonitor.main.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.redemonitor.main.dto.response.DispositivoResponse;
import com.redemonitor.main.mapper.DispositivoMapper;
import com.redemonitor.main.model.Dispositivo;
import com.redemonitor.main.service.TokenService;

@Component
public class DispositivoWebSocket {

	@Value("${config.websocket.dispositivos.topic}")
	private String dispositivosTopic;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
		
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private DispositivoMapper dispositivoMapper;
	
	public void sendMessage( Dispositivo dispositivo, String accessToken ) {
		String username = tokenService.getUsernameByAccessToken( accessToken );
						
		DispositivoResponse resp = dispositivoMapper.map( dispositivo );
        String wsMessage = dispositivoMapper.mapToString( resp );

        simpMessagingTemplate.convertAndSendToUser( username, dispositivosTopic, wsMessage );
	}
	
}
