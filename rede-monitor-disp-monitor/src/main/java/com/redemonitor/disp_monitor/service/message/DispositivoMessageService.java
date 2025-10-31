package com.redemonitor.disp_monitor.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redemonitor.disp_monitor.mapper.DispositivoMapper;
import com.redemonitor.disp_monitor.model.Dispositivo;
import com.redemonitor.disp_monitor.model.message.DispositivoMessage;
import com.redemonitor.disp_monitor.service.TokenService;

@Service
public class DispositivoMessageService {
	
	@Autowired
	private DispositivoMapper dispositivoMapper;
	
	@Autowired
	private TokenService tokenService;
	
	public void sendMessage( Dispositivo dispositivo, String accessToken ) {
		String username = tokenService.getUsernameByAccessToken( accessToken );
		
		DispositivoMessage message = DispositivoMessage.builder()
				.username( username )
				.id( dispositivo.getId() )
				.sendoMonitorado( dispositivo.isSendoMonitorado() )
				.status( dispositivo.getStatus() ) 
				.build();

        //simpMessagingTemplate.convertAndSendToUser( username, "/topic/dispositivo", message );
    }

}
