package com.redemonitor.mapper;

import com.redemonitor.dto.response.EventoResponse;
import com.redemonitor.model.Evento;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.util.Date;

@Component
public class EventoMapper {

    public EventoResponse map( Evento evento ) {
        return EventoResponse.builder()
                .id( evento.getId() )
                .sucessosQuant( evento.getSucessosQuant() )
                .falhasQuant( evento.getFalhasQuant() )
                .quedasQuant( evento.getQuedasQuant() )
                .tempoInatividade( evento.getTempoInatividade() )
                .duracao( evento.getDuracao() )
                .criadoEm( evento.getCriadoEm() )
                .build();
    }

}
