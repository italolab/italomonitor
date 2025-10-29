package com.redemonitor.main.mapper;

import org.springframework.stereotype.Component;

import com.redemonitor.main.dto.request.SaveUsuarioGrupoRequest;
import com.redemonitor.main.dto.response.UsuarioGrupoResponse;
import com.redemonitor.main.model.UsuarioGrupo;

@Component
public class UsuarioGrupoMapper {

    public UsuarioGrupo map(SaveUsuarioGrupoRequest request ) {
        return UsuarioGrupo.builder()
                .nome( request.getNome() )
                .build();
    }

    public UsuarioGrupoResponse map( UsuarioGrupo grupo ) {
        return UsuarioGrupoResponse.builder()
                .id( grupo.getId() )
                .nome( grupo.getNome() )
                .build();
    }

    public void load(UsuarioGrupo usuario, SaveUsuarioGrupoRequest request ) {
        usuario.setNome( request.getNome() );
    }

}
