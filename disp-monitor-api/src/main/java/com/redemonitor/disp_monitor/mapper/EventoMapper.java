package com.redemonitor.disp_monitor.mapper;

import org.springframework.stereotype.Component;

import com.redemonitor.disp_monitor.dto.request.integration.SaveEventoRequest;
import com.redemonitor.disp_monitor.model.Evento;

@Component
public class EventoMapper {

	public SaveEventoRequest map( Evento evento ) {
		return SaveEventoRequest.builder()
				.sucessosQuant( evento.getSucessosQuant() )
				.falhasQuant( evento.getFalhasQuant() )
				.quedasQuant( evento.getQuedasQuant() )
				.tempoInatividade( evento.getTempoInatividade() )
				.duracao( evento.getDuracao() )
				.criadoEm( evento.getCriadoEm() )
				.dispositivoId( evento.getDispositivoId() ) 
				.build();
	}
	
}
