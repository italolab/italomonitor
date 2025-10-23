package com.redemonitor.service.message;

import com.redemonitor.dto.response.DispositivoResponse;
import com.redemonitor.mapper.DispositivoMapper;
import com.redemonitor.model.Dispositivo;
import com.redemonitor.model.enums.DispositivoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class DispositivoMessageService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private DispositivoMapper dispositivoMapper;

    public void send( Dispositivo dispositivo, String username ) {
        DispositivoResponse resp = dispositivoMapper.map( dispositivo );
        String message = new ObjectMapper().writeValueAsString( resp );

        simpMessagingTemplate.convertAndSendToUser( username, "/topic/dispositivo", message );
    }

}
