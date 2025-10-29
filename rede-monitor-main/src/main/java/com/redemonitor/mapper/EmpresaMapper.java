package com.redemonitor.mapper;

import com.redemonitor.dto.request.SaveEmpresaRequest;
import com.redemonitor.dto.response.EmpresaResponse;
import com.redemonitor.model.Empresa;
import org.springframework.stereotype.Component;

@Component
public class EmpresaMapper {

    public Empresa map(SaveEmpresaRequest request) {
        return Empresa.builder()
                .nome( request.getNome() )
                .emailNotif( request.getEmailNotif() )
                .porcentagemMaxFalhasPorLote( request.getPorcentagemMaxFalhasPorLote() )
                .build();
    }

    public EmpresaResponse map( Empresa empresa ) {
        return EmpresaResponse.builder()
                .id( empresa.getId() )
                .nome( empresa.getNome() )
                .emailNotif( empresa.getEmailNotif() )
                .porcentagemMaxFalhasPorLote( empresa.getPorcentagemMaxFalhasPorLote() )
                .build();
    }

    public void load( Empresa empresa, SaveEmpresaRequest request ) {
        empresa.setNome( request.getNome() );
        empresa.setEmailNotif( request.getEmailNotif() );
        empresa.setPorcentagemMaxFalhasPorLote( request.getPorcentagemMaxFalhasPorLote() );
    }

}
