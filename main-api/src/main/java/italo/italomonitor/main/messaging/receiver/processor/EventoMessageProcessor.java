package italo.italomonitor.main.messaging.receiver.processor;

import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import italo.italomonitor.main.dto.integration.DispMonitorEvento;
import italo.italomonitor.main.exception.BusinessException;
import italo.italomonitor.main.exception.Errors;
import italo.italomonitor.main.mapper.EventoMapper;
import italo.italomonitor.main.model.Dispositivo;
import italo.italomonitor.main.model.Evento;
import italo.italomonitor.main.repository.DispositivoRepository;
import italo.italomonitor.main.repository.EventoRepository;

@Component
public class EventoMessageProcessor {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private DispositivoRepository dispositivoRepository;
			
	@Autowired
	private EventoMapper eventoMapper;
		
	public void processMessage( DispMonitorEvento message ) {
		try {
			Long dispositivoId = message.getDispositivoId();
			
			Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
			if ( dispositivoOp.isEmpty() )
				throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND+" ID="+dispositivoId );			
			
			Dispositivo dispositivo = dispositivoOp.get();
			
			Evento evento = eventoMapper.map( message );
			evento.setDispositivo( dispositivo ); 
					
			eventoRepository.save( evento );
		} catch ( BusinessException e ) {
			Logger.getLogger( EventoMessageProcessor.class ).debug( e.response().getMessage() ); 			
		} catch ( RuntimeException e ) {
			Logger.getLogger( EventoMessageProcessor.class ).error( e.getMessage() ); 
		}
	}
	
}
