package com.redemonitor.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.redemonitor.main.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query( "select u from Usuario u where lower(u.nome) like lower(?1)" )
    List<Usuario> filter( String nomePart );

    @Query( "select u from Usuario u where u.username=?1 and u.senha=?2")
    Optional<Usuario> findByLogin(String username, String senha );

    Optional<Usuario> findByUsername( String username );

}
