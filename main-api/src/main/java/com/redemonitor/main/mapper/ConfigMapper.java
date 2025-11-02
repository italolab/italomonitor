package com.redemonitor.main.mapper;

import org.springframework.stereotype.Component;

import com.redemonitor.main.dto.request.SaveConfigRequest;
import com.redemonitor.main.dto.response.ConfigResponse;
import com.redemonitor.main.model.Config;

@Component
public class ConfigMapper {

    public Config map( SaveConfigRequest request ) {
        return Config.builder()
                .numPacotesPorLote( request.getNumPacotesPorLote() )
                .monitoramentoDelay( request.getMonitoramentoDelay() )
                .registroEventoPeriodo( request.getRegistroEventoPeriodo() )
                .numThreadsLimite( request.getNumThreadsLimite() )
                .build();
    }

    public ConfigResponse map( Config config ) {
        return ConfigResponse.builder()
                .id( config.getId() )
                .numPacotesPorLote( config.getNumPacotesPorLote() )
                .monitoramentoDelay( config.getMonitoramentoDelay() )
                .registroEventoPeriodo( config.getRegistroEventoPeriodo() )
                .numThreadsLimite( config.getNumThreadsLimite() )
                .build();
    }

    public void load( Config config, SaveConfigRequest request ) {
        config.setNumPacotesPorLote( request.getNumPacotesPorLote() );
        config.setMonitoramentoDelay( request.getMonitoramentoDelay() );
        config.setRegistroEventoPeriodo( request.getRegistroEventoPeriodo() );
        config.setNumThreadsLimite( request.getNumThreadsLimite() ); 
    }

}
