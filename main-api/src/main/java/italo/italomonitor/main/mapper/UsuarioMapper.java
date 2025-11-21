package italo.italomonitor.main.mapper;

import org.springframework.stereotype.Component;

import italo.italomonitor.main.dto.request.CreateUsuarioRequest;
import italo.italomonitor.main.dto.request.UpdateUsuarioRequest;
import italo.italomonitor.main.dto.response.UsuarioResponse;
import italo.italomonitor.main.model.Usuario;

@Component
public class UsuarioMapper {

    public Usuario map(CreateUsuarioRequest request ) {
        return Usuario.builder()
                .nome( request.getNome() )
                .email( request.getEmail() )
                .username( request.getUsername() )
                .senha( request.getSenha() )
                .perfil( request.getPerfil() )
                .build();
    }

    public UsuarioResponse map( Usuario usuario ) {
        return UsuarioResponse.builder()
                .id( usuario.getId() )
                .nome( usuario.getNome() )
                .username( usuario.getUsername() )
                .email( usuario.getEmail() )
                .perfil( usuario.getPerfil() ) 
                .build();
    }

    public void load( Usuario usuario, UpdateUsuarioRequest request ) {
        usuario.setNome( request.getNome() );
        usuario.setEmail( request.getEmail() );
        usuario.setUsername( request.getUsername() );
        usuario.setPerfil( request.getPerfil() ); 
    }

}
