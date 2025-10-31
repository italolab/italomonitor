package com.redemonitor.disp_monitor.mapper;

import org.springframework.stereotype.Component;

import com.redemonitor.disp_monitor.integration.dto.request.SaveDispositivoStatusRequest;
import com.redemonitor.disp_monitor.integration.dto.response.DispositivoResponse;
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
                .empresa( 
                	Empresa.builder()
                		.porcentagemMaxFalhasPorLote( response.getEmpresa().getPorcentagemMaxFalhasPorLote() )
                		.build() )
                .build();
    }
    
    public SaveDispositivoStatusRequest map( Dispositivo dispositivo ) {
    	return SaveDispositivoStatusRequest.builder()
    			.sendoMonitorado( dispositivo.isSendoMonitorado() )
    			.status( dispositivo.getStatus() )
    			.build();
    }

}
