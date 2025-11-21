package italo.italomonitor.main.messaging.websocket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import italo.italomonitor.main.dto.response.DispositivoResponse;
import italo.italomonitor.main.mapper.DispositivoMapper;
import italo.italomonitor.main.messaging.websocket.handlers.WebSocketHandlerDecoratorFactory2;
import italo.italomonitor.main.model.Dispositivo;
import italo.italomonitor.main.model.Empresa;
import italo.italomonitor.main.repository.UsuarioRepository;

@Component
public class DispositivoWebSocket {

	@Value("${config.websocket.dispositivos.topic}")
	private String dispositivosTopic;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
				
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DispositivoMapper dispositivoMapper;
	
	@Autowired
	private WebSocketHandlerDecoratorFactory2 webSocketConnectionTracker;
	
	public void sendMessage( Dispositivo dispositivo ) {						
		DispositivoResponse resp = dispositivoMapper.map( dispositivo );
        String wsMessage = dispositivoMapper.mapToString( resp );
        
        Empresa empresa = dispositivo.getEmpresa();                
        Long empresaId = empresa.getId();                
               
		List<String> usernames = usuarioRepository.getUsernamesByEmpresa( empresaId );
        for( String username : usernames )
        	if ( webSocketConnectionTracker.verifySeConnected( username ) )   
        		simpMessagingTemplate.convertAndSendToUser( username, dispositivosTopic, wsMessage );        
	}	
		
}
