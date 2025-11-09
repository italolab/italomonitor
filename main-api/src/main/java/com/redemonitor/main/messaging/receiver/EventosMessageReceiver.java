package com.redemonitor.main.messaging.receiver;

import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.redemonitor.main.dto.integration.DispMonitorEvento;
import com.redemonitor.main.exception.Errors;
import com.redemonitor.main.mapper.EventoMapper;
import com.redemonitor.main.model.Dispositivo;
import com.redemonitor.main.model.Evento;
import com.redemonitor.main.repository.DispositivoRepository;
import com.redemonitor.main.repository.EventoRepository;

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
	}
	
}
