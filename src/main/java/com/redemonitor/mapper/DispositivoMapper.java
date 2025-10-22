package com.redemonitor.mapper;

import com.redemonitor.dto.request.SaveDispositivoRequest;
import com.redemonitor.dto.response.DispositivoResponse;
import com.redemonitor.model.Dispositivo;
import org.springframework.stereotype.Component;

@Component
public class DispositivoMapper {

    public Dispositivo map(SaveDispositivoRequest request ) {
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

    public void load( Dispositivo disp, SaveDispositivoRequest request ) {
        disp.setHost( request.getHost() );
        disp.setNome( request.getNome() );
        disp.setDescricao( request.getDescricao() );
        disp.setLocalizacao( request.getLocalizacao() );
    }

}
