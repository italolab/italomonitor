package italo.italomonitor.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import italo.italomonitor.main.model.RoleGrupoMap;

import java.util.Optional;

public interface RoleGrupoMapRepository extends JpaRepository<RoleGrupoMap, Long> {

    @Query( "select map from RoleGrupoMap map where map.role.id=?1 and map.usuarioGrupo.id=?2" )
    Optional<RoleGrupoMap> get( Long roleId, Long usuarioGrupoId );

}
