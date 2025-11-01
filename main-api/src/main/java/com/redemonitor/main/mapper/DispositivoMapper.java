package com.redemonitor.main.mapper;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redemonitor.main.dto.message.DispositivoMessage;
import com.redemonitor.main.dto.request.SaveDispositivoRequest;
import com.redemonitor.main.dto.request.SaveDispositivoStatusRequest;
import com.redemonitor.main.dto.response.DispositivoResponse;
import com.redemonitor.main.dto.response.EmpresaResponse;
import com.redemonitor.main.model.Dispositivo;
import com.redemonitor.main.model.Empresa;

@Component
public class DispositivoMapper {

	@Autowired
	private EmpresaMapper empresaMapper;
	
    public Dispositivo map( SaveDispositivoRequest request ) {
        return Dispositivo.builder()
                .host( request.getHost() )
                .nome( request.getNome() )
                .descricao( request.getDescricao() )
                .localizacao( request.getLocalizacao() )
                .build();
    }

    public DispositivoResponse map( Dispositivo dispositivo ) {
    	EmpresaResponse empresaResp = null;
    	
    	Empresa empresa = dispositivo.getEmpresa();
    	if ( empresa != null )
    		empresaResp = empresaMapper.map( empresa );    	
    	
        return DispositivoResponse.builder()
                .id( dispositivo.getId() )
                .host( dispositivo.getHost() )
                .nome( dispositivo.getNome() )
                .descricao( dispositivo.getDescricao() )
                .localizacao( dispositivo.getLocalizacao() )
                .sendoMonitorado( dispositivo.isSendoMonitorado() )
                .status( dispositivo.getStatus() )
                .empresa( empresaResp )
                .build();
    }

    public String mapToString( DispositivoResponse disp ) {
        try {
            return new ObjectMapper().writeValueAsString( disp );
        } catch ( JsonProcessingException e ) {
            Logger.getLogger(DispositivoMapper.class.getName()).log(Level.SEVERE, "Falha no processamento do JSON", e);
        }
        return null;
    }

    public void load( Dispositivo disp, SaveDispositivoRequest request ) {
        disp.setHost( request.getHost() );
        disp.setNome( request.getNome() );
        disp.setDescricao( request.getDescricao() );
        disp.setLocalizacao( request.getLocalizacao() );
    }
    
    public void load( Dispositivo disp, SaveDispositivoStatusRequest request ) {
    	disp.setSendoMonitorado( request.isSendoMonitorado() );
    	disp.setStatus( request.getStatus() ); 
    }
    
    public void load( Dispositivo disp, DispositivoMessage message ) {
    	disp.setSendoMonitorado( message.isSendoMonitorado() );
    	disp.setStatus( message.getStatus() ); 
    }

}
