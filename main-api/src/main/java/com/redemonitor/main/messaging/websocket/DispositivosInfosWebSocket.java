package com.redemonitor.main.messaging.websocket;

import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;

import com.redemonitor.main.dto.response.DispositivosInfosResponse;
import com.redemonitor.main.mapper.DispositivoMapper;
import com.redemonitor.main.repository.DispositivoRepository;
import com.redemonitor.main.repository.UsuarioRepository;

@Component
public class DispositivosInfosWebSocket {

	@Value("${config.websocket.dispositivos-infos.topic}")
	private String dispositivosInfosTopic;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DispositivoRepository dispositivoRepository;
	
	@Autowired
	private DispositivoMapper dispositivoMapper;
		
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private SimpUserRegistry simpUserRegistry;
					
	public void sendDispositivosInfosMessage( Long dispositivoId ) {						
		Optional<Long> empresaIDOp = dispositivoRepository.getEmpresaId( dispositivoId );
		if ( empresaIDOp.isEmpty() ) {
			Logger.getLogger( DispositivosInfosWebSocket.class ).error( "Dispositivo: "+dispositivoId+" sem empresa. " ); 
			return;
		}
					
		Long empresaId = empresaIDOp.get();
		
		boolean sendoMonitorado = true;
		int quantTotal = dispositivoRepository.countByEmpresa( empresaId );
		int sendoMonitoradosQuant = dispositivoRepository.countByEmpresaBySendoMonitorado( empresaId, sendoMonitorado );

		DispositivosInfosResponse resp = DispositivosInfosResponse.builder()
				.quantTotal( quantTotal )
				.sendoMonitoradosQuant( sendoMonitoradosQuant ) 
				.build();
		
		String wsMessage = dispositivoMapper.mapToString( resp ); 
		        		
        List<String> usernames = usuarioRepository.getUsernamesByEmpresa( empresaId );

        List<String> users = simpUserRegistry.getUsers().stream().map( u -> u.getName() ).toList();
        for( String username : usernames )
        	if ( users.contains( username ) ) 
        		simpMessagingTemplate.convertAndSendToUser( username, dispositivosInfosTopic, wsMessage );        				
	}
	
}

