package com.italomonitor.main.messaging.receiver;

import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.italomonitor.main.dto.integration.DispMonitorEvento;
import com.italomonitor.main.exception.Errors;
import com.italomonitor.main.mapper.EventoMapper;
import com.italomonitor.main.model.Dispositivo;
import com.italomonitor.main.model.Evento;
import com.italomonitor.main.repository.DispositivoRepository;
import com.italomonitor.main.repository.EventoRepository;

@Component
public class EventosMessageReceiver {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private DispositivoRepository dispositivoRepository;
			
	@Autowired
	private EventoMapper eventoMapper;
		
	@RabbitListener( queues = {"${config.rabbitmq.eventos.queue}"} ) 
	public void receivesMessage( @Payload DispMonitorEvento message ) {
		try {
			Long dispositivoId = message.getDispositivoId();
			
			Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
			if ( dispositivoOp.isEmpty() ) {
				Logger.getLogger( EventosMessageReceiver.class ).error( Errors.DISPOSITIVO_NOT_FOUND );
				return;
			}
			
			Dispositivo dispositivo = dispositivoOp.get();
			
			Evento evento = eventoMapper.map( message );
			evento.setDispositivo( dispositivo ); 
					
			eventoRepository.save( evento );
		} catch ( RuntimeException e ) {
			Logger.getLogger( DispositivosStateMessageReceiver.class ).error( e.getMessage() ); 
		}
	}
	
}
