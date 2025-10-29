package com.redemonitor.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.redemonitor.main.model.Dispositivo;

import java.util.List;
import java.util.Optional;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {

    @Query( "select d from Dispositivo d where lower(d.host) like lower(?1) and " +
            "lower(d.nome) like lower(?2) and lower(d.localizacao) like lower(?3)" )
    List<Dispositivo> filter( String hostPart, String nomePart, String localPart );

    Optional<Dispositivo> findByNome( String nome );

}
