package com.redemonitor.main.messaging.receiver;

import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.redemonitor.main.dto.integration.DispMonitorDispositivoState;
import com.redemonitor.main.dto.response.DispositivoResponse;
import com.redemonitor.main.exception.BusinessException;
import com.redemonitor.main.exception.Errors;
import com.redemonitor.main.mapper.DispositivoMapper;
import com.redemonitor.main.model.Dispositivo;
import com.redemonitor.main.model.Empresa;
import com.redemonitor.main.repository.DispositivoRepository;
import com.redemonitor.main.repository.UsuarioRepository;

@Component
public class DispositivosStateMessageReceiver {

	@Value("${config.websocket.dispositivos.topic}")
	private String dispositivosTopic;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private DispositivoRepository dispositivoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DispositivoMapper dispositivoMapper;
	
	@RabbitListener( queues = {"${config.rabbitmq.dispositivos-state.queue}"} ) 
	public void receivesMessage( @Payload DispMonitorDispositivoState message ) {
		Long dispositivoId = message.getId();
						
		Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
		if ( dispositivoOp.isEmpty() )
			throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );
		
		Dispositivo dispositivo = dispositivoOp.get();
		dispositivoMapper.load( dispositivo, message ); 
		
		dispositivoRepository.save( dispositivo );
		
		DispositivoResponse resp = dispositivoMapper.map( dispositivo );
        String wsMessage = dispositivoMapper.mapToString( resp );
        
        Empresa empresa = dispositivo.getEmpresa();
        Long empresaId = empresa.getId();
        
        List<String> usernames = usuarioRepository.getUsernamesByEmpresa( empresaId );
        for( String username : usernames )
        	simpMessagingTemplate.convertAndSendToUser( username, dispositivosTopic, wsMessage );        
	}
	
}
