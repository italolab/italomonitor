package com.italomonitor.main.mapper;

import org.springframework.stereotype.Component;

import com.italomonitor.main.dto.integration.DispMonitorEvento;
import com.italomonitor.main.dto.request.SaveEventoRequest;
import com.italomonitor.main.dto.response.EventoResponse;
import com.italomonitor.main.model.Evento;

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
	
	public Evento map( DispMonitorEvento message ) {
		return Evento.builder()
				.sucessosQuant( message.getSucessosQuant() )
				.falhasQuant( message.getFalhasQuant() )
				.quedasQuant( message.getQuedasQuant() )
				.tempoInatividade( message.getTempoInatividade() )
				.duracao( message.getDuracao() )
				.criadoEm( message.getCriadoEm() )				
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
