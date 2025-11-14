package com.italomonitor.main.messaging.receiver;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.italomonitor.main.components.util.DateUtil;
import com.italomonitor.main.dto.integration.DispMonitorDispositivoState;
import com.italomonitor.main.dto.response.DispositivoResponse;
import com.italomonitor.main.enums.DispositivoStatus;
import com.italomonitor.main.exception.BusinessException;
import com.italomonitor.main.exception.Errors;
import com.italomonitor.main.integration.TelegramIntegration;
import com.italomonitor.main.mapper.DispositivoMapper;
import com.italomonitor.main.messaging.email.EMailSender;
import com.italomonitor.main.model.Config;
import com.italomonitor.main.model.Dispositivo;
import com.italomonitor.main.model.Empresa;
import com.italomonitor.main.repository.ConfigRepository;
import com.italomonitor.main.repository.DispositivoRepository;
import com.italomonitor.main.repository.EmpresaRepository;
import com.italomonitor.main.repository.UsuarioRepository;

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
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private DispositivoMapper dispositivoMapper;
	
	@Autowired
	private ConfigRepository configRepository;
		
	@Autowired
	private DateUtil dateUtil;
	
	@Autowired
	private TelegramIntegration telegramIntegration;
	
	@Autowired
	private EMailSender emailSender;
		
	@RabbitListener( queues = {"${config.rabbitmq.dispositivos-state.queue}"} ) 
	public void receivesMessage( @Payload DispMonitorDispositivoState message ) {
		try {
			Long dispositivoId = message.getId();
							
			Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
			if ( dispositivoOp.isEmpty() )
				throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND+" ID="+dispositivoId );
			
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
	        
	        this.sendNotifSeNecessario( dispositivo, empresa );
		} catch ( BusinessException e ) {
			Logger.getLogger( DispositivosStateMessageReceiver.class ).debug( e.response().getMessage() ); 			
		} catch ( RuntimeException e ) {
			Logger.getLogger( DispositivosStateMessageReceiver.class ).error( e.getMessage() ); 
		} 
	}
	
	private void sendNotifSeNecessario( Dispositivo dispositivo, Empresa empresa ) {
		Long empresaId = empresa.getId();
		
		int minTempoParaProxNotif = empresa.getMinTempoParaProxNotif();
		
		Date ultNotifEm = empresa.getUltimaNotifEm();
		if ( ultNotifEm == null ) {
			empresa.setUltimaNotifEm( new Date() );
			empresaRepository.save( empresa );
			
			ultNotifEm = empresa.getUltimaNotifEm();
		}
				
		LocalDateTime ultimaNotifEm = dateUtil.dateToLocalDateTime( ultNotifEm );
		LocalDateTime ultimaNotifEmAdded = ultimaNotifEm.plusSeconds( minTempoParaProxNotif );
		
		if ( LocalDateTime.now().isAfter( ultimaNotifEmAdded ) ) {																	
			empresaRepository.updateUltimaNotifEm( empresaId, new Date() ); 
			
			Config config = configRepository.findFirstByOrderByIdAsc();
			String telegramBotToken = config.getTelegramBotToken();

			String chatId = empresa.getTelegramChatId();
			String emailNotif = empresa.getEmailNotif();
			
			StringBuilder mensagemBuilder = new StringBuilder();
			
			String dataAtual = dateUtil.dateTimeFormat( LocalDateTime.now() );
			mensagemBuilder.append( "Data de emissão: "+dataAtual+"\n\n" );
			mensagemBuilder.append( "Dispositivos inativos: \n" );
			
			List<Object[]> disps = dispositivoRepository.getNomesAndStatusesByEmpresaId( empresaId );
			
			boolean temInativo = false;
			for( Object[] disp : disps ) {
				String nome = disp[0].toString();
				DispositivoStatus status = (DispositivoStatus)disp[1];
				if ( status == DispositivoStatus.INATIVO ) {
					mensagemBuilder.append( "\t"+nome+"\n" );
					temInativo = true;
				}
			}															
				
			if ( temInativo ) {
				String mensagem = mensagemBuilder.toString();
								
				try {
					telegramIntegration.sendMessage( telegramBotToken, chatId, mensagem );
				} catch ( BusinessException e ) {
					String notifSubject = "Chat do telegram inacessível.";
					String notifMessage = "Não foi possível enviar a mensagem via telegram. Verifique a configuração do seu id de chat";
					emailSender.sendEMail( emailNotif, notifSubject, notifMessage );
				} catch ( RestClientException e ) {
					Logger.getLogger( DispositivosStateMessageReceiver.class ).error( "Não foi possível acessar a api do telegram.\nErro="+e.getMessage() );
				}
				
				String subject = "Notificação de dispositivos inativos";
				emailSender.sendEMail( emailNotif, subject, mensagem ); 
			}						
		}
	}
	
}
