package com.redemonitor.main.messaging.websocket;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;

import com.redemonitor.main.dto.response.DispositivoResponse;
import com.redemonitor.main.mapper.DispositivoMapper;
import com.redemonitor.main.model.Dispositivo;
import com.redemonitor.main.model.Empresa;
import com.redemonitor.main.repository.UsuarioRepository;

@Component
public class DispositivoWebSocket {

	@Value("${config.websocket.dispositivos.topic}")
	private String dispositivosTopic;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private SimpUserRegistry simpUserRegistry;
			
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DispositivoMapper dispositivoMapper;
	
	public void sendMessage( Dispositivo dispositivo ) {						
		DispositivoResponse resp = dispositivoMapper.map( dispositivo );
        String wsMessage = dispositivoMapper.mapToString( resp );
        
        Empresa empresa = dispositivo.getEmpresa();
        Long empresaId = empresa.getId();                
               
		List<String> usernames = usuarioRepository.getUsernamesByEmpresa( empresaId );
        for( String username : usernames )
        	if ( this.existsUserSessionByUsername( username ) )  
        		simpMessagingTemplate.convertAndSendToUser( username, dispositivosTopic, wsMessage );        
	}	
	
	private boolean existsUserSessionByUsername( String username ) {
		 Set<SimpUser> users = simpUserRegistry.getUsers();
	     for( SimpUser user : users )
	    	 if ( user.getName().equals( username ) )
	        	return true;
	     return false;
	}
	
}
