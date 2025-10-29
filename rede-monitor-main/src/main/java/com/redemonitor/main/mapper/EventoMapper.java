package com.redemonitor.main.mapper;

import org.springframework.stereotype.Component;

import com.redemonitor.main.dto.response.EventoResponse;
import com.redemonitor.main.model.Evento;

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
