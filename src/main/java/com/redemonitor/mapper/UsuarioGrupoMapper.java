package com.redemonitor.mapper;

import com.redemonitor.dto.request.SaveUsuarioGrupoRequest;
import com.redemonitor.dto.response.UsuarioGrupoResponse;
import com.redemonitor.model.UsuarioGrupo;
import org.springframework.stereotype.Component;

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
