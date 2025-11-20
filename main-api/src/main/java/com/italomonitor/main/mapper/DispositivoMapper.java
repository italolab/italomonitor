package com.italomonitor.main.mapper;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.italomonitor.main.dto.integration.DispMonitorDispositivo;
import com.italomonitor.main.dto.integration.DispMonitorDispositivoState;
import com.italomonitor.main.dto.integration.DispMonitorEmpresa;
import com.italomonitor.main.dto.request.SaveDispositivoRequest;
import com.italomonitor.main.dto.response.AgenteResponse;
import com.italomonitor.main.dto.response.DispositivoResponse;
import com.italomonitor.main.dto.response.DispositivosInfosResponse;
import com.italomonitor.main.dto.response.EmpresaResponse;
import com.italomonitor.main.enums.DispositivoStatus;
import com.italomonitor.main.model.Agente;
import com.italomonitor.main.model.Dispositivo;
import com.italomonitor.main.model.Empresa;

@Component
public class DispositivoMapper {

	@Autowired
	private EmpresaMapper empresaMapper;
	
	@Autowired
	private AgenteMapper agenteMapper;
	
    public Dispositivo map( SaveDispositivoRequest request ) {
        return Dispositivo.builder()
                .host( request.getHost() )
                .nome( request.getNome() )
                .descricao( request.getDescricao() )
                .localizacao( request.getLocalizacao() )
                .status( DispositivoStatus.INATIVO )
                .monitoradoPorAgente( request.isMonitoradoPorAgente() )                
                .build();
    }

    public DispositivoResponse map( Dispositivo dispositivo ) {
    	EmpresaResponse empresaResp = null;
    	AgenteResponse agenteResp = null;
    	
    	Empresa empresa = dispositivo.getEmpresa();
    	if ( empresa != null )
    		empresaResp = empresaMapper.map( empresa );    	
    	
    	Agente agente = dispositivo.getAgente();
    	if ( agente != null )
    		agenteResp = agenteMapper.map( agente );
    	
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
                .monitoradoPorAgente( dispositivo.isMonitoradoPorAgente() ) 
                .empresa( empresaResp )
                .agente( agenteResp )
                .build();
    }
    
    public DispMonitorDispositivo mapToDispMonitorDispositivo( Dispositivo dispositivo ) {
    	DispMonitorEmpresa dmEmpresa = null;
    	
    	Empresa empresa = dispositivo.getEmpresa();
    	if ( empresa != null )
    		dmEmpresa = empresaMapper.mapToDispMonitorEmpresa( empresa );    	
    	
        return DispMonitorDispositivo.builder()
                .id( dispositivo.getId() )
                .host( dispositivo.getHost() )
                .nome( dispositivo.getNome() )
                .descricao( dispositivo.getDescricao() )
                .localizacao( dispositivo.getLocalizacao() )
                .sendoMonitorado( dispositivo.isSendoMonitorado() )
                .status( dispositivo.getStatus() )
                .latenciaMedia( dispositivo.getLatenciaMedia() )
                .stateAtualizadoEm( dispositivo.getStateAtualizadoEm() ) 
                .empresa( dmEmpresa )
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
    
    public String mapToString( DispositivosInfosResponse disp ) {
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
        disp.setMonitoradoPorAgente( request.isMonitoradoPorAgente() ); 
    }
     
    public void load( Dispositivo disp, DispMonitorDispositivoState message ) {
    	disp.setStatus( message.getStatus() ); 
    	disp.setLatenciaMedia( message.getLatenciaMedia() ); 
    	disp.setStateAtualizadoEm( new Date() ); 
    }

}
