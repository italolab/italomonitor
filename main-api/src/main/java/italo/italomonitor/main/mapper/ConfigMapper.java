package italo.italomonitor.main.mapper;

import org.springframework.stereotype.Component;

import italo.italomonitor.main.dto.integration.DispMonitorConfig;
import italo.italomonitor.main.dto.request.SaveConfigRequest;
import italo.italomonitor.main.dto.response.ConfigResponse;
import italo.italomonitor.main.dto.response.NoAdminConfigResponse;
import italo.italomonitor.main.model.Config;

@Component
public class ConfigMapper {

    public Config map( SaveConfigRequest request ) {
        return Config.builder()
                .numPacotesPorLote( request.getNumPacotesPorLote() )
                .monitoramentoDelay( request.getMonitoramentoDelay() )
                .registroEventoPeriodo( request.getRegistroEventoPeriodo() )
                .numThreadsLimite( request.getNumThreadsLimite() )
                .telegramBotToken( request.getTelegramBotToken() )
                .valorPagto( request.getValorPagto() )
                .build();
    }

    public ConfigResponse map( Config config ) {
        return ConfigResponse.builder()
                .id( config.getId() )
                .numPacotesPorLote( config.getNumPacotesPorLote() )
                .monitoramentoDelay( config.getMonitoramentoDelay() )
                .registroEventoPeriodo( config.getRegistroEventoPeriodo() )
                .numThreadsLimite( config.getNumThreadsLimite() )
                .telegramBotToken( config.getTelegramBotToken() )
                .valorPagto( config.getValorPagto() )
                .build();
    }
    
    public NoAdminConfigResponse mapToNoAdmin( Config config ) {
    	return NoAdminConfigResponse.builder()
    			.valorPagto( config.getValorPagto() )
    			.build();
    }

    public DispMonitorConfig mapToDispMonitorConfig( Config config ) {
    	return DispMonitorConfig.builder()
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
        config.setTelegramBotToken( request.getTelegramBotToken() );
        config.setValorPagto( request.getValorPagto() ); 
    }

}
