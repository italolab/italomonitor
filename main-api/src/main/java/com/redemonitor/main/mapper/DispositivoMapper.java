package com.redemonitor.main.mapper;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redemonitor.main.dto.message.DispositivoMessage;
import com.redemonitor.main.dto.request.SaveDispositivoRequest;
import com.redemonitor.main.dto.request.SaveDispositivoStateRequest;
import com.redemonitor.main.dto.response.DispositivoResponse;
import com.redemonitor.main.dto.response.EmpresaResponse;
import com.redemonitor.main.enums.DispositivoStatus;
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
                .status( DispositivoStatus.INATIVO )                
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
                .latenciaMedia( dispositivo.getLatenciaMedia() )
                .stateAtualizadoEm( dispositivo.getStateAtualizadoEm() ) 
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
        disp.setStatus( DispositivoStatus.INATIVO ); 
    }
    
    public void load( Dispositivo disp, SaveDispositivoStateRequest request ) {
    	disp.setSendoMonitorado( request.isSendoMonitorado() );
    	disp.setStatus( request.getStatus() );
    	disp.setLatenciaMedia( request.getLatenciaMedia() );
    	disp.setStateAtualizadoEm( new Date() ); 
    }
    
    public void load( Dispositivo disp, DispositivoMessage message ) {
    	disp.setStatus( message.getStatus() ); 
    	disp.setLatenciaMedia( message.getLatenciaMedia() ); 
    	disp.setStateAtualizadoEm( new Date() ); 
    }

}
