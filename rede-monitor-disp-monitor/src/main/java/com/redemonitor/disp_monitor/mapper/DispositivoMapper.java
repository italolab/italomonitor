package com.redemonitor.disp_monitor.mapper;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    
    public String mapToString( DispositivoResponse disp ) {
        try {
            return new ObjectMapper().writeValueAsString( disp );
        } catch ( JsonProcessingException e ) {
            Logger.getLogger(DispositivoMapper.class.getName()).log(Level.SEVERE, "Falha no processamento do JSON", e);
        }
        return null;
    }

}
