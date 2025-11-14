package com.italomonitor.main.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.italomonitor.main.constants.DateConstants;
import com.italomonitor.main.dto.request.SaveEventoRequest;
import com.italomonitor.main.dto.response.EventoResponse;
import com.italomonitor.main.exception.BusinessException;
import com.italomonitor.main.exception.Errors;
import com.italomonitor.main.mapper.EventoMapper;
import com.italomonitor.main.model.Dispositivo;
import com.italomonitor.main.model.Evento;
import com.italomonitor.main.repository.DispositivoRepository;
import com.italomonitor.main.repository.EventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;
    
    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private EventoMapper eventoMapper;

    public void createEvento( SaveEventoRequest request ) {
    	Long dispositivoId = request.getDispositivoId();
    	Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
    	if ( dispositivoOp.isEmpty() )
    		throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );
    	
    	Dispositivo dispositivo = dispositivoOp.get();
    	
    	Evento evento = eventoMapper.map( request );
    	evento.setDispositivo( dispositivo );
    	
    	eventoRepository.save( evento );    	
    }
    
    public EventoResponse getEvento(Long eventoId ) {
        Optional<Evento> eventoOp = eventoRepository.findById( eventoId );
        if ( eventoOp.isEmpty() )
            throw new BusinessException( Errors.EVENT_NOT_FOUND );

        return eventoOp.map( eventoMapper::map ).orElseThrow();
    }

    public List<EventoResponse> listByIntervalo( 
    		Long dispositivoId, 
    		LocalDate dataDiaIni, 
    		LocalDate dataDiaFim, 
    		boolean ascendente ) {
    	
        LocalDateTime dataHoraIni = dataDiaIni.atStartOfDay();
        LocalDateTime dataHoraFim = dataDiaFim.atStartOfDay().plusSeconds( DateConstants.PLUS_END_DIA_SEGUNDOS );
        List<Evento> eventos;
        if ( ascendente )
        	eventos = eventoRepository.listByIntervalo( dispositivoId, dataHoraIni, dataHoraFim );
        else eventos = eventoRepository.listByIntervaloOrdemInversa( dispositivoId, dataHoraIni, dataHoraFim );
        return eventos.stream().map( eventoMapper::map ).toList();
    }
    
    public List<EventoResponse> listByDia( Long dispositivoId, LocalDate dataDia ) {
        LocalDateTime dataHoraIni = dataDia.atStartOfDay();
        LocalDateTime dataHoraFim = dataHoraIni.plusSeconds( DateConstants.PLUS_END_DIA_SEGUNDOS );
        List<Evento> eventos = eventoRepository.listByIntervalo( dispositivoId, dataHoraIni, dataHoraFim );
        return eventos.stream().map( eventoMapper::map ).toList();
    }

}
