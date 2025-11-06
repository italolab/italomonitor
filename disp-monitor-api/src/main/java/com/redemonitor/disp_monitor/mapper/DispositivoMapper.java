package com.redemonitor.disp_monitor.mapper;

import org.springframework.stereotype.Component;

import com.redemonitor.disp_monitor.dto.request.integration.SaveDispositivoStateRequest;
import com.redemonitor.disp_monitor.dto.response.integration.DispositivoResponse;
import com.redemonitor.disp_monitor.model.Dispositivo;
import com.redemonitor.disp_monitor.model.Empresa;

@Component
public class DispositivoMapper {

    public Dispositivo map( DispositivoResponse response ) {
    	return Dispositivo.builder()
                .id( response.getId() )
                .host( response.getHost() )
                .nome( response.getNome() )
                .descricao( response.getDescricao() )
                .localizacao( response.getLocalizacao() )
                .sendoMonitorado( response.isSendoMonitorado() )
                .status( response.getStatus() ) 
                .latenciaMedia( response.getLatenciaMedia() )
                .stateAtualizadoEm( response.getStateAtualizadoEm() ) 
                .empresa( 
                	Empresa.builder()
                		.porcentagemMaxFalhasPorLote( response.getEmpresa().getPorcentagemMaxFalhasPorLote() )
                		.build() )
                .build();
    }
    
    public SaveDispositivoStateRequest map( Dispositivo dispositivo ) {
    	return SaveDispositivoStateRequest.builder()
    			.sendoMonitorado( dispositivo.isSendoMonitorado() )
    			.status( dispositivo.getStatus() )
    			.latenciaMedia( dispositivo.getLatenciaMedia() ) 
    			.build();
    }

}
