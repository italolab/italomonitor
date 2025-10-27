package com.redemonitor.repository;

import com.redemonitor.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query( "select e from Evento e where e.dispositivo.id=?1 and e.criadoEm between ?2 and ?3" )
    List<Evento> listByIntervalo( Long dispositivoId, LocalDateTime dataHoraIni, LocalDateTime dataHoraFim );

}
