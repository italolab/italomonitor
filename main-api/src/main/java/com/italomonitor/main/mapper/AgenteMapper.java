package com.italomonitor.main.mapper;

import org.springframework.stereotype.Component;

import com.italomonitor.main.dto.request.SaveAgenteRequest;
import com.italomonitor.main.dto.response.AgenteResponse;
import com.italomonitor.main.model.Agente;

@Component
public class AgenteMapper {

	public Agente map( SaveAgenteRequest request ) {
		return Agente.builder()
				.nome( request.getNome() ) 
				.build();
	}
	
	public AgenteResponse map( Agente agente ) {
		return AgenteResponse.builder()
				.id( agente.getId() )
				.chave( agente.getChave() )
				.nome( agente.getNome() ) 
				.build();
	}
	
	public void load( Agente agente, SaveAgenteRequest request ) {
		agente.setNome( request.getNome() ); 
	}
	
}
