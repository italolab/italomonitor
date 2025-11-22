package italo.italomonitor.main.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import italo.italomonitor.main.dto.integration.DispMonitorAgente;
import italo.italomonitor.main.dto.request.SaveAgenteRequest;
import italo.italomonitor.main.dto.response.AgenteResponse;
import italo.italomonitor.main.model.Agente;

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
	
	public DispMonitorAgente mapToDispMonitorAgente( Agente agente, List<Long> dispositivosIDs ) {
		return DispMonitorAgente.builder()
				.chave( agente.getChave() )
				.dispositivosIDs( dispositivosIDs )
 				.build();
	}
	
	public void load( Agente agente, SaveAgenteRequest request ) {
		agente.setNome( request.getNome() ); 
	}
	
}
