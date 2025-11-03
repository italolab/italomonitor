package com.redemonitor.main.components;

import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.redemonitor.main.enums.MonitoramentoOperResult;
import com.redemonitor.main.exception.BusinessException;
import com.redemonitor.main.exception.ErrorException;
import com.redemonitor.main.exception.Errors;
import com.redemonitor.main.integration.DispositivoMonitorIntegration;
import com.redemonitor.main.integration.dto.ExisteNoMonitorResponse;
import com.redemonitor.main.integration.dto.InfoResponse;
import com.redemonitor.main.integration.dto.MonitoramentoOperResponse;
import com.redemonitor.main.messaging.DispositivoWebSocket;
import com.redemonitor.main.model.Config;
import com.redemonitor.main.model.Dispositivo;
import com.redemonitor.main.model.MonitorServer;
import com.redemonitor.main.repository.ConfigRepository;
import com.redemonitor.main.repository.DispositivoRepository;
import com.redemonitor.main.repository.MonitorServerRepository;

@Component
public class DispositivoMonitorEscalonador {
		
	@Autowired
	private DispositivoMonitorIntegration dispositivoMonitorIntegration;
	
	@Autowired
	private ConfigRepository configRepository;
	
	@Autowired
	private MonitorServerRepository monitorServerRepository;
	
	@Autowired
	private DispositivoRepository dispositivoRepository;
	
	@Autowired
	private DispositivoWebSocket dispositivoWebSocket;
	
	public void startAllMonitoramentos( Long empresaId, String accessToken ) {
		Config config = configRepository.findFirstByOrderByIdAsc();
		List<MonitorServer> monitorServers = monitorServerRepository.findAll();
		
		List<Long> dispsIDs = dispositivoRepository.findIDsByEmpresaId( empresaId );
		int dispsQuant = dispsIDs.size();
		int dispsMonitQuant = 0;
		for( Long dispositivoId : dispsIDs ) {
			MonitoramentoOperResult result = this.startMonitoramento( dispositivoId, accessToken, config, monitorServers );
			switch( result ) {
				case INICIADO:
					dispsMonitQuant++;
					break;		            
				case EXCEDE_LIMITE:
					throw new BusinessException( Errors.DISPOSITIVO_START_EXCEDE_LIMITE, ""+dispsMonitQuant, ""+dispsQuant );
				default:
					break;		
			}
			
		}
	}
	
	public void stopAllMonitoramentos( Long empresaId, String accessToken ) {
		List<MonitorServer> monitorServers = monitorServerRepository.findAll();
		
		List<Long> dispIDs = dispositivoRepository.findIDsByEmpresaId( empresaId );
		for( Long dispositivoId : dispIDs )
			this.stopMonitoramento( dispositivoId, accessToken, monitorServers );		
	}
	
	public void startMonitoramento( Long dispositivoId, String accessToken ) {
		Config config = configRepository.findFirstByOrderByIdAsc();
		List<MonitorServer> monitorServers = monitorServerRepository.findAll();
		
		MonitoramentoOperResult result = this.startMonitoramento( dispositivoId, accessToken, config, monitorServers );
		switch( result ) {
			case JA_INICIADO:
	            throw new BusinessException( Errors.DISPOSITIVO_ALREADY_MONITORED );
			case EXCEDE_LIMITE:
				throw new BusinessException( Errors.DISPOSITIVO_START_EXCEDE_LIMITE, "0", "1" );
			default:
				break;		
		}
	}
	
	public void stopMonitoramento( Long dispositivoId, String accessToken ) {
		List<MonitorServer> monitorServers = monitorServerRepository.findAll();

		MonitoramentoOperResult result = this.stopMonitoramento( dispositivoId, accessToken, monitorServers );
		switch( result ) {
			case NAO_ENCONTRADO:
		        throw new BusinessException( Errors.DISPOSITIVO_NOT_MONITORED );		       
		    default:
		    	break;
		}
	}
	
	public MonitoramentoOperResult startMonitoramento( 
			Long dispositivoId, 
			String username, 
			Config config, 
			List<MonitorServer> monitorServers ) {
		
		if ( this.verificaSeSendoMonitorado( dispositivoId, monitorServers ) ) {
			this.updateDispositivo( dispositivoId, true, username );  
		
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
				resp = dispositivoMonitorIntegration.startMonitoramento( host, dispositivoId, username );
			} catch ( RestClientException e ) {				
				Logger.getLogger( DispositivoMonitorEscalonador.class ).error( "Servidor inacessível = "+host );					
			}
						
			current = ( current+1 ) % serversQuant;
			
			if ( resp != null ) { 
				switch( resp.getResult() ) {
					case INICIADO:
						config.setMonitorServerCorrente( current );
						configRepository.save( config );
												
						startou = true;
						break;
					case JA_INICIADO:
						this.updateDispositivo( dispositivoId, true, username );  
		
						return MonitoramentoOperResult.JA_INICIADO;					
					default:
						throw new ErrorException( "Status de start monitoramento inválido. Status="+resp.getResult() );
				}
			}
			
			isFirst = false;
		}
				
		if ( naoStartou )
			return MonitoramentoOperResult.EXCEDE_LIMITE;		
		return MonitoramentoOperResult.INICIADO;
	}
	
	public MonitoramentoOperResult stopMonitoramento( Long dispositivoId, String username, List<MonitorServer> monitorServers ) {		
		for( MonitorServer server : monitorServers ) {
			String host = server.getHost();
			
			try {
				MonitoramentoOperResponse resp = dispositivoMonitorIntegration.stopMonitoramento( host, dispositivoId );				
				if ( resp.getResult() == MonitoramentoOperResult.FINALIZADO )				
					return MonitoramentoOperResult.FINALIZADO;
			} catch ( RestClientException e ) {
				Logger.getLogger( DispositivoMonitorEscalonador.class ).error( "Servidor inacessível = "+host );					
			}															
		}
		
		this.updateDispositivo( dispositivoId, false, username );
			
		return MonitoramentoOperResult.NAO_ENCONTRADO;
	}
	
	public void updateConfigInMonitores() {
		List<MonitorServer> monitorServers = monitorServerRepository.findAll();
		
		for( MonitorServer server : monitorServers ) {	
			String host = server.getHost();
			
			try {
				MonitoramentoOperResponse resp = dispositivoMonitorIntegration.updateConfigInMonitores( host );							
				if ( resp.getResult() == MonitoramentoOperResult.ATUALIZADO )
					return;
			} catch ( RestClientException e ) {
				Logger.getLogger( DispositivoMonitorEscalonador.class ).error( "Servidor inacessível = "+host );					
			}
		}		
	}
	
	public void updateDispositivoInMonitor( Long dispositivoId ) {
		List<MonitorServer> monitorServers = monitorServerRepository.findAll();
		
		for( MonitorServer server : monitorServers ) {	
			String host = server.getHost();
			
			try {
				MonitoramentoOperResponse resp = dispositivoMonitorIntegration.updateDispositivoInMonitor( host, dispositivoId );						
				if ( resp.getResult() == MonitoramentoOperResult.ATUALIZADO )
					return;
			} catch ( RestClientException e ) {
				Logger.getLogger( DispositivoMonitorEscalonador.class ).error( "Servidor inacessível = "+host );					
			}
		}		
	}
		
	public MonitorInfo getInfo( String serverHost ) {
		try {
			InfoResponse resp = dispositivoMonitorIntegration.getInfo( serverHost );
			return new MonitorInfo( resp.getNumThreadsAtivas(), true );
		} catch ( RestClientException e ) {
			return new MonitorInfo( 0, false );
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
	
	private void updateDispositivo( Long dispositivoId, boolean sendoMonitorado, String username ) {
		Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
		if ( dispositivoOp.isEmpty() )
			throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );
		
		Dispositivo dispositivo = dispositivoOp.get();
		dispositivo.setSendoMonitorado( sendoMonitorado );
        dispositivoRepository.save( dispositivo );

        dispositivoWebSocket.sendMessage( dispositivo, username );
	}
	
	public static class MonitorInfo {
		
		private final int numThreadsAtivas;
		private final boolean ativo;
		
		public MonitorInfo( int numThreadsAtivas, boolean ativo ) {
			this.numThreadsAtivas = numThreadsAtivas;
			this.ativo = ativo;
		}
		
		public int getNumThreadsAtivas() {
			return numThreadsAtivas;
		}
		
		public boolean isAtivo() {
			return ativo;
		}
		
	}
		
}
