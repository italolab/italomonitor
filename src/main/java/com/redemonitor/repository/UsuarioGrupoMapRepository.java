package com.redemonitor.repository;

import com.redemonitor.model.UsuarioGrupoMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioGrupoMapRepository extends JpaRepository<UsuarioGrupoMap, Long> {

    @Query( "select map from UsuarioGrupoMap map where map.usuario.id=?1 and map.usuarioGrupo.id=?2" )
    Optional<UsuarioGrupoMap> get( Long usuarioId, Long usuarioGrupoId );

}
