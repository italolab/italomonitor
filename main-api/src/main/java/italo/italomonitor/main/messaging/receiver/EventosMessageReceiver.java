package italo.italomonitor.main.messaging.receiver;

import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
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
			if ( dispositivoOp.isEmpty() )
				throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND+" ID="+dispositivoId );			
			
			Dispositivo dispositivo = dispositivoOp.get();
			
			Evento evento = eventoMapper.map( message );
			evento.setDispositivo( dispositivo ); 
					
			eventoRepository.save( evento );
		} catch ( BusinessException e ) {
			Logger.getLogger( DispositivosStateMessageReceiver.class ).debug( e.response().getMessage() ); 			
		} catch ( RuntimeException e ) {
			Logger.getLogger( DispositivosStateMessageReceiver.class ).error( e.getMessage() ); 
		}
	}
	
}
