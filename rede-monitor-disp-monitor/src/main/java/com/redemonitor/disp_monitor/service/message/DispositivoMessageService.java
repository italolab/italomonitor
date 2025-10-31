package com.redemonitor.disp_monitor.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redemonitor.disp_monitor.mapper.DispositivoMapper;
import com.redemonitor.disp_monitor.model.Dispositivo;
import com.redemonitor.disp_monitor.model.message.DispositivoMessage;

@Service
public class DispositivoMessageService {
	
	@Autowired
	private DispositivoMapper dispositivoMapper;
	
	public void sendMessage( Dispositivo dispositivo, String username ) {
		DispositivoMessage message = DispositivoMessage.builder()
				.username( username )
				.id( dispositivo.getId() )
				.sendoMonitorado( dispositivo.isSendoMonitorado() )
				.status( dispositivo.getStatus() ) 
				.build();

        //simpMessagingTemplate.convertAndSendToUser( username, "/topic/dispositivo", message );
    }

}
