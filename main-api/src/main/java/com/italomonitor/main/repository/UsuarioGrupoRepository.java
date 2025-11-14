package com.italomonitor.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.italomonitor.main.model.UsuarioGrupo;

public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupo, Long> {

    @Query( "select g from UsuarioGrupo g where lower(g.nome) like lower(?1)" )
    List<UsuarioGrupo> filter( String nomePart );

    Optional<UsuarioGrupo> findByNome( String nome );

}
