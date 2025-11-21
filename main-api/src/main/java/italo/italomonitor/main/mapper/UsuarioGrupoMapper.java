package italo.italomonitor.main.mapper;

import org.springframework.stereotype.Component;

import italo.italomonitor.main.dto.request.SaveUsuarioGrupoRequest;
import italo.italomonitor.main.dto.response.UsuarioGrupoResponse;
import italo.italomonitor.main.model.UsuarioGrupo;

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
