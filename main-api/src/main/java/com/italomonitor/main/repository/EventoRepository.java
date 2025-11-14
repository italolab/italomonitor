package com.italomonitor.main.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.italomonitor.main.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query( "select e from Evento e where e.dispositivo.id=?1 and e.criadoEm between ?2 and ?3 order by e.criadoEm" )
    List<Evento> listByIntervalo( Long dispositivoId, LocalDateTime dataHoraIni, LocalDateTime dataHoraFim );

    @Query( "select e from Evento e where e.dispositivo.id=?1 and e.criadoEm between ?2 and ?3 order by e.criadoEm desc" )
    List<Evento> listByIntervaloOrdemInversa( Long dispositivoId, LocalDateTime dataHoraIni, LocalDateTime dataHoraFim );

    @Query( "select e.dispositivo.empresa.id from Evento e where e.id=?1")
    Optional<Long> getEmpresaId( Long eventoId );
    
}
