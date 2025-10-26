package com.redemonitor.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redemonitor.dto.request.SaveDispositivoRequest;
import com.redemonitor.dto.response.DispositivoResponse;
import com.redemonitor.model.Dispositivo;
import com.redemonitor.service.device.DispositivoMonitorThread;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DispositivoMapper {



    public Dispositivo map( SaveDispositivoRequest request ) {
        return Dispositivo.builder()
                .host( request.getHost() )
                .nome( request.getNome() )
                .descricao( request.getDescricao() )
                .localizacao( request.getLocalizacao() )
                .build();
    }

    public DispositivoResponse map( Dispositivo dispositivo ) {
        return DispositivoResponse.builder()
                .id( dispositivo.getId() )
                .host( dispositivo.getHost() )
                .nome( dispositivo.getNome() )
                .descricao( dispositivo.getDescricao() )
                .localizacao( dispositivo.getLocalizacao() )
                .sendoMonitorado( dispositivo.isSendoMonitorado() )
                .status( dispositivo.getStatus() )
                .build();
    }

    public String mapToString( DispositivoResponse disp ) {
        try {
            return new ObjectMapper().writeValueAsString( disp );
        } catch ( JsonProcessingException e ) {
            Logger.getLogger(DispositivoMapper.class.getName()).log(Level.SEVERE, "Falha no processamento do JSON", e);
        }
        return null;
    }

    public void load( Dispositivo disp, SaveDispositivoRequest request ) {
        disp.setHost( request.getHost() );
        disp.setNome( request.getNome() );
        disp.setDescricao( request.getDescricao() );
        disp.setLocalizacao( request.getLocalizacao() );
    }

}
