package com.redemonitor.main.mapper;

import org.springframework.stereotype.Component;

import com.redemonitor.main.dto.request.SaveEventoRequest;
import com.redemonitor.main.dto.response.EventoResponse;
import com.redemonitor.main.model.Evento;

@Component
public class EventoMapper {

	public Evento map( SaveEventoRequest request ) {
		return Evento.builder()
				.sucessosQuant( request.getSucessosQuant() )
				.falhasQuant( request.getFalhasQuant() )
				.quedasQuant( request.getQuedasQuant() )
				.tempoInatividade( request.getTempoInatividade() )
				.duracao( request.getDuracao() )
				.criadoEm( request.getCriadoEm() )
				.build();
	}
	
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
