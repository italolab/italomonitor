package italo.italomonitor.main.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import italo.italomonitor.main.dto.integration.DispMonitorAgente;
import italo.italomonitor.main.dto.integration.DispMonitorConfig;
import italo.italomonitor.main.dto.integration.DispMonitorDispositivo;
import italo.italomonitor.main.dto.integration.DispMonitorDispositivoState;
import italo.italomonitor.main.dto.integration.DispMonitorEvento;
import italo.italomonitor.main.exception.BusinessException;
import italo.italomonitor.main.exception.Errors;
import italo.italomonitor.main.mapper.AgenteMapper;
import italo.italomonitor.main.mapper.ConfigMapper;
import italo.italomonitor.main.mapper.DispositivoMapper;
import italo.italomonitor.main.messaging.receiver.processor.DispositivoStateMessageProcessor;
import italo.italomonitor.main.messaging.receiver.processor.EventoMessageProcessor;
import italo.italomonitor.main.model.Agente;
import italo.italomonitor.main.model.Config;
import italo.italomonitor.main.model.Dispositivo;
import italo.italomonitor.main.repository.AgenteRepository;
import italo.italomonitor.main.repository.ConfigRepository;
import italo.italomonitor.main.repository.DispositivoRepository;

@Service
public class AgenteMonitorService {
	
	@Autowired
	private DispositivoStateMessageProcessor dispositivoStateMessageProcessor;
	
	@Autowired
	private EventoMessageProcessor eventoMessageProcessor;
	
	@Autowired
	private AgenteRepository agenteRepository;
	
	@Autowired
	private DispositivoRepository dispositivoRepository;
	
	@Autowired
	private ConfigRepository configRepository;
	
	@Autowired
	private DispositivoMapper dispositivoMapper;
	
	@Autowired
	private ConfigMapper configMapper;
	
	@Autowired
	private AgenteMapper agenteMapper;
	
	public DispMonitorAgente getAgente( String agenteChave ) {
		Optional<Agente> agenteOp = agenteRepository.findByChave( agenteChave );
		if ( agenteOp.isEmpty() )
			throw new BusinessException( Errors.AGENTE_NOT_FOUND );
		
		Agente agente = agenteOp.get();
				
		List<Long> dispositivosIDs = dispositivoRepository.findIDsByAgenteID( agente.getId() );
		
		return agenteMapper.mapToDispMonitorAgente( agente, dispositivosIDs );		
	}
	
	public DispMonitorConfig getConfig( String agenteChave ) {
		Optional<Agente> agenteOp = agenteRepository.findByChave( agenteChave );
		if ( agenteOp.isEmpty() )
			throw new BusinessException( Errors.AGENTE_NOT_FOUND );
		
		Config config = configRepository.findFirstByOrderByIdAsc();
		
		return configMapper.mapToDispMonitorConfig( config );
	}
	
	public DispMonitorDispositivo getDispositivo( String agenteChave, Long dispositivoId ) {
		Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
		if ( dispositivoOp.isEmpty() )
			throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );
		
		Dispositivo dispositivo = dispositivoOp.get();
		if ( !dispositivo.isMonitoradoPorAgente() )
			throw new BusinessException( Errors.DISPOSITIVO_MONITORADO_POR_AGENTE );
		
		Agente agente = dispositivo.getAgente();
		if ( agente == null )
			throw new BusinessException( Errors.DISPOSITIVO_MONITORADO_POR_AGENTE );
		
		if ( !agente.getChave().equals( agenteChave ) )
			throw new BusinessException( Errors.DISPOSITIVO_NAO_ADICIONADO_AO_AGENTE );
		
		return dispositivoMapper.mapToDispMonitorDispositivo( dispositivo );		
	}
	
	public void processaDispositivoState( String agenteChave, DispMonitorDispositivoState dispState ) {
		Optional<Agente> agenteOp = agenteRepository.findByChave( agenteChave );
		if ( agenteOp.isEmpty() )
			throw new BusinessException( Errors.AGENTE_NOT_FOUND );
		
		Agente agente = agenteOp.get();
		agente.setUltimoEnvioDeEstadoEm( new Date() );
		
		agenteRepository.save( agente );
		agente = agenteRepository.findById( agente.getId() ).get();
		
		dispositivoStateMessageProcessor.processMessage( dispState ); 
	}
	
	public void processaEvento( String agenteChave, DispMonitorEvento evento ) {
		eventoMessageProcessor.processMessage( evento );
	}

}
