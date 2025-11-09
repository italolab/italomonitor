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
                .minTempoParaProximoEvento( request.getMinTempoParaProximoEvento() )
                .diaPagto( request.getDiaPagto() )
                .temporario( request.isTemporario() )
                .usoTemporarioPor( request.getUsoTemporarioPor() )
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
                .minTempoParaProximoEvento( empresa.getMinTempoParaProximoEvento() )
                .diaPagto( empresa.getDiaPagto() )
                .temporario( empresa.isTemporario() )
                .usoTemporarioPor( empresa.getUsoTemporarioPor() )
                .criadoEm( empresa.getCriadoEm() )
                .build();
    }
    
    public DispMonitorEmpresa mapToDispMonitorEmpresa( Empresa empresa ) {
    	return DispMonitorEmpresa.builder()
    			 .id( empresa.getId() )
                 .nome( empresa.getNome() )
                 .emailNotif( empresa.getEmailNotif() )
                 .porcentagemMaxFalhasPorLote( empresa.getPorcentagemMaxFalhasPorLote() )
                 .maxDispositivosQuant( empresa.getMaxDispositivosQuant() )
    			.build();
    }

    public void load( Empresa empresa, SaveEmpresaRequest request ) {
        empresa.setNome( request.getNome() );
        empresa.setEmailNotif( request.getEmailNotif() );
        empresa.setTelegramChatId( request.getTelegramChatId() );
        empresa.setPorcentagemMaxFalhasPorLote( request.getPorcentagemMaxFalhasPorLote() );
        empresa.setMaxDispositivosQuant( request.getMaxDispositivosQuant() );
        empresa.setMinTempoParaProximoEvento( request.getMinTempoParaProximoEvento() );
        empresa.setDiaPagto( request.getDiaPagto() );
        empresa.setTemporario( request.isTemporario() );
        empresa.setUsoTemporarioPor( request.getUsoTemporarioPor() );
    }
    
    public void load( Empresa empresa, NoAdminSaveEmpresaRequest request ) {
    	empresa.setNome( request.getNome() );
        empresa.setEmailNotif( request.getEmailNotif() );
        empresa.setTelegramChatId( request.getTelegramChatId() );
        empresa.setPorcentagemMaxFalhasPorLote( request.getPorcentagemMaxFalhasPorLote() );
        empresa.setMinTempoParaProximoEvento( request.getMinTempoParaProximoEvento() ); 
    }

}
