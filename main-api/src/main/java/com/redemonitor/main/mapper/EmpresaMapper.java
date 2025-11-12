package com.redemonitor.main.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.redemonitor.main.dto.integration.DispMonitorEmpresa;
import com.redemonitor.main.dto.request.NoAdminSaveEmpresaRequest;
import com.redemonitor.main.dto.request.SaveEmpresaRequest;
import com.redemonitor.main.dto.response.EmpresaResponse;
import com.redemonitor.main.model.Empresa;

@Component
public class EmpresaMapper {

    public Empresa map( SaveEmpresaRequest request ) {
        return Empresa.builder()
                .nome( request.getNome() )
                .emailNotif( request.getEmailNotif() )
                .telegramChatId( request.getTelegramChatId() )
                .porcentagemMaxFalhasPorLote( request.getPorcentagemMaxFalhasPorLote() )
                .maxDispositivosQuant( request.getMaxDispositivosQuant() )
                .minTempoParaProxNotif( request.getMinTempoParaProxNotif() )
                .diaPagto( request.getDiaPagto() )
                .temporario( request.isTemporario() )
                .usoTemporarioPor( request.getUsoTemporarioPor() )
                .bloqueada( request.isBloqueada() )
                .criadoEm( new Date() ) 
                .build();
    }
    
    public EmpresaResponse map( Empresa empresa ) {
        return EmpresaResponse.builder()
                .id( empresa.getId() )
                .nome( empresa.getNome() )
                .emailNotif( empresa.getEmailNotif() )
                .telegramChatId( empresa.getTelegramChatId() )
                .porcentagemMaxFalhasPorLote( empresa.getPorcentagemMaxFalhasPorLote() )
                .maxDispositivosQuant( empresa.getMaxDispositivosQuant() )
                .minTempoParaProxNotif( empresa.getMinTempoParaProxNotif() )
                .diaPagto( empresa.getDiaPagto() )
                .temporario( empresa.isTemporario() )
                .usoTemporarioPor( empresa.getUsoTemporarioPor() )
                .bloqueada( empresa.isBloqueada() )
                .criadoEm( empresa.getCriadoEm() )
                .build();
    }
    
    public DispMonitorEmpresa mapToDispMonitorEmpresa( Empresa empresa ) {
    	return DispMonitorEmpresa.builder()
    			 .id( empresa.getId() )
                 .porcentagemMaxFalhasPorLote( empresa.getPorcentagemMaxFalhasPorLote() )
    			.build();
    }

    public void load( Empresa empresa, SaveEmpresaRequest request ) {
        empresa.setNome( request.getNome() );
        empresa.setEmailNotif( request.getEmailNotif() );
        empresa.setTelegramChatId( request.getTelegramChatId() );
        empresa.setPorcentagemMaxFalhasPorLote( request.getPorcentagemMaxFalhasPorLote() );
        empresa.setMaxDispositivosQuant( request.getMaxDispositivosQuant() );
        empresa.setMinTempoParaProxNotif( request.getMinTempoParaProxNotif() );
        empresa.setDiaPagto( request.getDiaPagto() );
        empresa.setTemporario( request.isTemporario() );
        empresa.setUsoTemporarioPor( request.getUsoTemporarioPor() );
        empresa.setBloqueada( request.isBloqueada() );
    }
    
    public void load( Empresa empresa, NoAdminSaveEmpresaRequest request ) {
    	empresa.setNome( request.getNome() );
        empresa.setEmailNotif( request.getEmailNotif() );
        empresa.setTelegramChatId( request.getTelegramChatId() );
        empresa.setPorcentagemMaxFalhasPorLote( request.getPorcentagemMaxFalhasPorLote() );
        empresa.setMinTempoParaProxNotif( request.getMinTempoParaProxNotif() );        
    }

}
