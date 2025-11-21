package italo.italomonitor.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import italo.italomonitor.main.model.UsuarioGrupoMap;

import java.util.Optional;

public interface UsuarioGrupoMapRepository extends JpaRepository<UsuarioGrupoMap, Long> {

    @Query( "select map from UsuarioGrupoMap map where map.usuario.id=?1 and map.usuarioGrupo.id=?2" )
    Optional<UsuarioGrupoMap> get( Long usuarioId, Long usuarioGrupoId );

}
