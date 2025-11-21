package italo.italomonitor.main.components;

import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import italo.italomonitor.main.dto.integration.response.ExisteNoMonitorResponse;
import italo.italomonitor.main.dto.integration.response.MonitorServerInfoResponse;
import italo.italomonitor.main.dto.integration.response.MonitoramentoOperResponse;
import italo.italomonitor.main.enums.MonitoramentoOperResult;
import italo.italomonitor.main.exception.BusinessException;
import italo.italomonitor.main.exception.ErrorException;
import italo.italomonitor.main.exception.Errors;
import italo.italomonitor.main.integration.DispositivoMonitorIntegration;
import italo.italomonitor.main.messaging.sender.ConfigMessageSender;
import italo.italomonitor.main.messaging.sender.DispositivoMessageSender;
import italo.italomonitor.main.messaging.websocket.DispositivoWebSocket;
import italo.italomonitor.main.messaging.websocket.DispositivosInfosWebSocket;
import italo.italomonitor.main.model.Config;
import italo.italomonitor.main.model.Dispositivo;
import italo.italomonitor.main.model.MonitorServer;
import italo.italomonitor.main.repository.ConfigRepository;
import italo.italomonitor.main.repository.DispositivoRepository;
import italo.italomonitor.main.repository.MonitorServerRepository;

@Component
public class DispositivoMonitorEscalonador {
		
	@Autowired
	private DispositivoMonitorIntegration dispositivoMonitorIntegration;
	
	@Autowired
	private DispositivoMessageSender dispositivoMessageSender;
	
	@Autowired
	private DispositivosInfosWebSocket dispositivosInfosWebSocket;
	
	@Autowired
	private ConfigMessageSender configMessageSender;
	
	@Autowired
	private ConfigRepository configRepository;
	
	@Autowired
	private MonitorServerRepository monitorServerRepository;
	
	@Autowired
	private DispositivoRepository dispositivoRepository;
	
	@Autowired
	private DispositivoWebSocket dispositivosWebSocket;
	
	public String startAllMonitoramentos() {
		Config config = configRepository.findFirstByOrderByIdAsc();
		List<MonitorServer> monitorServers = monitorServerRepository.findAll();
		
    	List<Long> dispsIDs = dispositivoRepository.findAllIDsNoMonitByAgente();
    	
    	int dispsQuant = dispsIDs.size();
    	
		Logger.getLogger( DispositivoMonitorEscalonador.class ).info( dispsQuant + " dispositivos para startar o monitoramento." ); 
    	
		int dispsMonitQuant = 0;
    	for( Long dispositivoId : dispsIDs ) {
    		MonitoramentoOperResult result = this.startMonitoramento( dispositivoId, config, monitorServers );
			switch( result ) {
				case INICIADO:
					dispsMonitQuant++;
					break;		            
				case EXCEDE_LIMITE: // excede o limite em todos os monitor_servers
					BusinessException ex = new BusinessException( Errors.DISPOSITIVO_START_EXCEDE_LIMITE, ""+dispsMonitQuant, ""+dispsQuant ); 
					Logger.getLogger( DispositivoMonitorEscalonador.class ).error( ex.response().getMessage() );
					
					throw ex;
				default:
					break;					
			}
    	}
    	
    	return dispsMonitQuant + " monitoramento(s) de dispositivo startado(s) com sucesso.";
	}
	
	public String stopAllMonitoramentos() {
		List<MonitorServer> monitorServers = monitorServerRepository.findAll();
		    	
		Logger.getLogger( DispositivoMonitorEscalonador.class ).info( " Parando todos os monitoramentos de dispositivo." ); 
    	
		for( MonitorServer server : monitorServers ) {
			String host = server.getHost();
			dispositivoMonitorIntegration.stopAllMonitoramentos( host ); 
		}				
		
		dispositivoRepository.updateAllToNaoSendoMonitorado();
		
		List<Long> ids = dispositivoRepository.findAllIDsNoMonitByAgente();
		for( long dispId : ids )
			dispositivosInfosWebSocket.sendDispositivosInfosMessage( dispId );		
    	
    	return "Todos os monitoramentos de dispositivo foram parados com sucesso.";
	}
	
	public void startEmpresaMonitoramentos( Long empresaId ) {
		Config config = configRepository.findFirstByOrderByIdAsc();
		List<MonitorServer> monitorServers = monitorServerRepository.findAll();
		
		List<Long> dispsIDs = dispositivoRepository.findIDsByEmpresaIdNoMonitByAgente( empresaId );
		int dispsQuant = dispsIDs.size();
		int dispsMonitQuant = 0;
		for( Long dispositivoId : dispsIDs ) {
			MonitoramentoOperResult result = this.startMonitoramento( dispositivoId, config, monitorServers );
			switch( result ) {
				case INICIADO:
					dispsMonitQuant++;
					break;		            
				case EXCEDE_LIMITE: // excede o limite em todos os monitor_servers
					BusinessException ex = new BusinessException( Errors.DISPOSITIVO_START_EXCEDE_LIMITE, ""+dispsMonitQuant, ""+dispsQuant ); 
					Logger.getLogger( DispositivoMonitorEscalonador.class ).error( ex.getMessage() );
					
					throw ex;
				default:
					break;		
			}
			
		}
	}
	
	public void stopEmpresaMonitoramentos( Long empresaId ) {
		List<MonitorServer> monitorServers = monitorServerRepository.findAll();
		
		List<Long> dispIDs = dispositivoRepository.findIDsByEmpresaIdNoMonitByAgente( empresaId );
		for( Long dispositivoId : dispIDs )
			this.stopMonitoramento( dispositivoId, monitorServers );		
	}
	
	public void startMonitoramento( Long dispositivoId ) {
		Config config = configRepository.findFirstByOrderByIdAsc();
		List<MonitorServer> monitorServers = monitorServerRepository.findAll();
		
		MonitoramentoOperResult result = this.startMonitoramento( dispositivoId, config, monitorServers );
		switch( result ) {
			case MONITORADO_POR_AGENTE:
				throw new BusinessException( Errors.DISPOSITIVO_MONITORADO_POR_AGENTE );
			case JA_INICIADO:
	            throw new BusinessException( Errors.DISPOSITIVO_ALREADY_MONITORED );
			case EXCEDE_LIMITE: // excede o limite em todos os monitor_servers
				BusinessException ex = new BusinessException( Errors.DISPOSITIVO_START_EXCEDE_LIMITE, "0", "1" ); 
				Logger.getLogger( DispositivoMonitorEscalonador.class ).error( ex.getMessage() );
				
				throw ex;
			default:
				break;		
		}
	}
	
	public void stopMonitoramento( Long dispositivoId ) {
		List<MonitorServer> monitorServers = monitorServerRepository.findAll();

		MonitoramentoOperResult result = this.stopMonitoramento( dispositivoId, monitorServers );
		switch( result ) {
			case NAO_ENCONTRADO:
		        throw new BusinessException( Errors.DISPOSITIVO_NOT_MONITORED );		       
		    default:
		    	break;
		}
	}
	
	public MonitoramentoOperResult startMonitoramento( 
			Long dispositivoId, 
			Config config, 
			List<MonitorServer> monitorServers ) {
			
		Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
		if ( dispositivoOp.isEmpty() )
			throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );
		
		Dispositivo dispositivo = dispositivoOp.get();
						
		if ( this.verificaSeSendoMonitorado( dispositivoId, monitorServers ) ) {
			this.updateDispositivo( dispositivo, true );  
		
			return MonitoramentoOperResult.JA_INICIADO;
		}
				
		int serversQuant = monitorServers.size();
		
		int current = config.getMonitorServerCorrente();
		if ( current >= serversQuant )
			throw new ErrorException( Errors.INVALID_MONITOR_SERVER_CURRENT_VALUE );
						
		boolean startou = false;
		boolean naoStartou = false;
		boolean isFirst = true;
		while( !startou && !naoStartou ) {
			if ( !isFirst && current == config.getMonitorServerCorrente() ) {
				naoStartou = true;
				continue;
			}
			
			MonitorServer server = monitorServers.get( current );
			String host = server.getHost();
					
			MonitoramentoOperResponse resp = null;
			
			try {
				resp = dispositivoMonitorIntegration.startMonitoramento( host, dispositivo, config );
			} catch ( RestClientException e ) {		
				Logger.getLogger( DispositivoMonitorEscalonador.class ).error( "Servidor inacessível = "+host );					
			}
						
			current = ( current+1 ) % serversQuant;
			
			if ( resp != null ) { 
				switch( resp.getResult() ) {
					case INICIADO:
						this.updateDispositivo( dispositivo, true );
						
						config.setMonitorServerCorrente( current );
						configRepository.save( config );
						
						dispositivosInfosWebSocket.sendDispositivosInfosMessage( dispositivoId );
												
						return MonitoramentoOperResult.INICIADO;
					case JA_INICIADO:
						this.updateDispositivo( dispositivo, true );  
		
						return MonitoramentoOperResult.JA_INICIADO;	
					case EXCEDE_LIMITE: // excede o limite apenas no monitor_server atual
						break;
					case MONITORADO_POR_AGENTE:
						return MonitoramentoOperResult.MONITORADO_POR_AGENTE;
					default:
						throw new ErrorException( "Status de start monitoramento inválido. Status="+resp.getResult() );
				}
			}
			
			isFirst = false;
		}
		
		return MonitoramentoOperResult.EXCEDE_LIMITE;		
	}
	
	public MonitoramentoOperResult stopMonitoramento( Long dispositivoId, List<MonitorServer> monitorServers ) {
		Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
		if ( dispositivoOp.isEmpty() )
			throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );
		
		Dispositivo dispositivo = dispositivoOp.get();
				
		for( MonitorServer server : monitorServers ) {
			String host = server.getHost();
			
			try {
				MonitoramentoOperResponse resp = dispositivoMonitorIntegration.stopMonitoramento( host, dispositivoId );				
				if ( resp.getResult() == MonitoramentoOperResult.FINALIZADO ) {
					this.updateDispositivo( dispositivo, false );
					
					dispositivosInfosWebSocket.sendDispositivosInfosMessage( dispositivoId );

					return MonitoramentoOperResult.FINALIZADO;
				}
			} catch ( RestClientException e ) {
				Logger.getLogger( DispositivoMonitorEscalonador.class ).error( "Servidor inacessível = "+host );					
			}															
		}
		
		this.updateDispositivo( dispositivo, false );
			
		return MonitoramentoOperResult.NAO_ENCONTRADO;
	}
	
	public void updateConfigInMonitores( Config config ) {		
		configMessageSender.sendMessage( config );															
	}
	
	public void updateDispositivoInMonitor( Dispositivo dispositivo ) {
		dispositivoMessageSender.sendMessage( dispositivo ); 		
	}
		
	public MonitorInfo getInfo( String serverHost ) {
		try {
			MonitorServerInfoResponse resp = dispositivoMonitorIntegration.getInfo( serverHost );
			return new MonitorInfo( resp, true );
		} catch ( RestClientException e ) {
			return new MonitorInfo( null, false );
		}
	}
	
	private boolean verificaSeSendoMonitorado( Long dispositivoId, List<MonitorServer> monitorServers ) {		
		for( MonitorServer server : monitorServers ) {	
			String host = server.getHost();
			
			try {
				ExisteNoMonitorResponse resp = dispositivoMonitorIntegration.getExisteNoMonitor( host, dispositivoId );							
				if ( resp.isExiste() )
					return true;
			} catch ( RestClientException e ) {
				Logger.getLogger( DispositivoMonitorEscalonador.class ).error( "Servidor inacessível = "+host );					
			}
		}	
		
		return false;
	}
	
	private void updateDispositivo( Dispositivo dispositivo, boolean sendoMonitorado ) {		
		dispositivo.setSendoMonitorado( sendoMonitorado );
        dispositivoRepository.save( dispositivo );

        dispositivosWebSocket.sendMessage( dispositivo );
                
        this.updateDispositivoInMonitor( dispositivo ); 
	}
	
	public static class MonitorInfo {
		
		private final boolean ativo;
		private final MonitorServerInfoResponse info;
		
		public MonitorInfo( MonitorServerInfoResponse info, boolean ativo ) {
			this.info = info;
			this.ativo = ativo;
		}
		
		public MonitorServerInfoResponse getInfo() {
			return info;
		}
		
		public boolean isAtivo() {
			return ativo;
		}
		
	}
		
}
