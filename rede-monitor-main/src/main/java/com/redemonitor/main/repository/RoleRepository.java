package com.redemonitor.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.redemonitor.main.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query( "select r from Role r where lower(r.nome) like lower(?1)" )
    List<Role> filter( String nomePart );

    Optional<Role> findByNome( String nome );

}
