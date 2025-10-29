package com.redemonitor.repository;

import com.redemonitor.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Query( "select emp from Empresa emp where lower(emp.nome) like lower(?1)" )
    List<Empresa> filter( String nomePart );

    public Optional<Empresa> findByNome(String nome );

}
