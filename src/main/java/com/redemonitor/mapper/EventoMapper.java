package com.redemonitor.mapper;

import com.redemonitor.dto.response.EventoResponse;
import com.redemonitor.model.Evento;
import org.springframework.stereotype.Component;

@Component
public class EventoMapper {

    public EventoResponse map( Evento evento ) {
        return EventoResponse.builder()
                .id( evento.getId() )
                .sucessosQuant( evento.getSucessosQuant() )
                .falhasQuant( evento.getFalhasQuant() )
                .quedasQuant( evento.getQuedasQuant() )
                .tempoInatividade( evento.getTempoInatividade() )
                .criadoEm( evento.getCriadoEm() )
                .build();
    }

}
