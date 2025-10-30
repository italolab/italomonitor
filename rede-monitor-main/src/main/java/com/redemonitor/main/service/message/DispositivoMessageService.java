package com.redemonitor.main.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.redemonitor.main.dto.response.DispositivoResponse;
import com.redemonitor.main.mapper.DispositivoMapper;
import com.redemonitor.main.model.Dispositivo;

@Service
public class DispositivoMessageService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private DispositivoMapper dispositivoMapper;

    public void send( Dispositivo dispositivo, String username ) {
        DispositivoResponse resp = dispositivoMapper.map( dispositivo );
        String message = dispositivoMapper.mapToString( resp );

        simpMessagingTemplate.convertAndSendToUser( username, "/topic/dispositivo", message );
    }

}
