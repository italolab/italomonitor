package com.redemonitor.main.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redemonitor.main.dto.response.DispositivoResponse;
import com.redemonitor.main.mapper.DispositivoMapper;
import com.redemonitor.main.model.Dispositivo;
import com.redemonitor.main.service.TokenService;
import com.redemonitor.main.service.device.DispositivoMonitorThread;

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
