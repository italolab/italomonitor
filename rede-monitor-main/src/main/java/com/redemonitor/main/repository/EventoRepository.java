package com.redemonitor.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.redemonitor.main.model.Evento;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query( "select e from Evento e where e.dispositivo.id=?1 and e.criadoEm between ?2 and ?3 order by e.criadoEm" )
    List<Evento> listByIntervalo( Long dispositivoId, LocalDateTime dataHoraIni, LocalDateTime dataHoraFim );

}
