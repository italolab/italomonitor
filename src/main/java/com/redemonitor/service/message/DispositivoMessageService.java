package com.redemonitor.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redemonitor.dto.response.DispositivoResponse;
import com.redemonitor.mapper.DispositivoMapper;
import com.redemonitor.model.Dispositivo;
import com.redemonitor.service.TokenService;
import com.redemonitor.service.device.DispositivoMonitorThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DispositivoMessageService {

    @Autowired
    private TokenService tokenService;

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
