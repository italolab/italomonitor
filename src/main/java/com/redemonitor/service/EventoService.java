package com.redemonitor.service;

import com.redemonitor.constants.DateConstants;
import com.redemonitor.dto.response.EventoResponse;
import com.redemonitor.exception.BusinessException;
import com.redemonitor.exception.Errors;
import com.redemonitor.mapper.EventoMapper;
import com.redemonitor.model.Evento;
import com.redemonitor.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoMapper eventoMapper;

    public EventoResponse getEvento(Long eventoId ) {
        Optional<Evento> eventoOp = eventoRepository.findById( eventoId );
        if ( eventoOp.isEmpty() )
            throw new BusinessException( Errors.EVENT_NOT_FOUND );

        return eventoOp.map( eventoMapper::map ).orElseThrow();
    }

    public List<EventoResponse> listByIntervalo( Long dispositivoId, LocalDate dataDiaIni, LocalDate dataDiaFim ) {
        LocalDateTime dataHoraIni = dataDiaIni.atStartOfDay();
        LocalDateTime dataHoraFim = dataDiaFim.atStartOfDay().plusSeconds( DateConstants.PLUS_END_DIA_SEGUNDOS );
        List<Evento> eventos = eventoRepository.listByIntervalo( dispositivoId, dataHoraIni, dataHoraFim );
        return eventos.stream().map( eventoMapper::map ).toList();
    }

    public List<EventoResponse> listByDia( Long dispositivoId, LocalDate dataDia ) {
        LocalDateTime dataHoraIni = dataDia.atStartOfDay();
        LocalDateTime dataHoraFim = dataHoraIni.plusSeconds( DateConstants.PLUS_END_DIA_SEGUNDOS );
        List<Evento> eventos = eventoRepository.listByIntervalo( dispositivoId, dataHoraIni, dataHoraFim );
        return eventos.stream().map( eventoMapper::map ).toList();
    }

}
