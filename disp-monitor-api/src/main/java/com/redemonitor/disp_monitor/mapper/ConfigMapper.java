package com.redemonitor.disp_monitor.mapper;

import org.springframework.stereotype.Component;

import com.redemonitor.disp_monitor.dto.response.integration.ConfigResponse;
import com.redemonitor.disp_monitor.model.Config;

@Component
public class ConfigMapper {

	public Config map( ConfigResponse resp ) {
		return Config.builder()
				.id( resp.getId() )
				.monitoramentoDelay( resp.getMonitoramentoDelay() )
				.numPacotesPorLote( resp.getNumPacotesPorLote() )
				.registroEventoPeriodo( resp.getRegistroEventoPeriodo() ) 
				.numThreadsLimite( resp.getNumThreadsLimite() )
				.build();
	}
	
}
