package italo.italomonitor.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import italo.italomonitor.main.components.DispositivoMonitorEscalonador;
import italo.italomonitor.main.dto.request.SaveDispositivoRequest;
import italo.italomonitor.main.dto.response.DispositivoResponse;
import italo.italomonitor.main.dto.response.DispositivosInfosResponse;
import italo.italomonitor.main.exception.BusinessException;
import italo.italomonitor.main.exception.Errors;
import italo.italomonitor.main.mapper.DispositivoMapper;
import italo.italomonitor.main.mapper.EmpresaMapper;
import italo.italomonitor.main.model.Agente;
import italo.italomonitor.main.model.Dispositivo;
import italo.italomonitor.main.model.Empresa;
import italo.italomonitor.main.repository.AgenteRepository;
import italo.italomonitor.main.repository.DispositivoRepository;
import italo.italomonitor.main.repository.EmpresaRepository;

@Service
public class DispositivoService {

    @Autowired
    private DispositivoMonitorEscalonador dispositivoMonitorEscalonador;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private DispositivoMapper dispositivoMapper;

    @Autowired
    private EmpresaMapper empresaMapper;
    
    public DispositivosInfosResponse getDispositivosInfos( Long empresaId ) {
    	boolean sendoMonitorado = true;
    	
    	int quantTotal = dispositivoRepository.countByEmpresa( empresaId );
    	int sendoMonitoradosQuant = dispositivoRepository.countByEmpresaBySendoMonitorado( empresaId, sendoMonitorado );
    	
    	return DispositivosInfosResponse.builder()
    			.quantTotal( quantTotal )
    			.sendoMonitoradosQuant( sendoMonitoradosQuant )
    			.build();
    }
    
    public void createDispositivo( SaveDispositivoRequest request ) {
        request.validate();

        Dispositivo dispositivo = dispositivoMapper.map( request );
        String nome = dispositivo.getNome();
        
        Empresa empresa;        
        if ( request.isMonitoradoPorAgente() ) {
        	Long agenteId = request.getAgenteId();        
        	Optional<Agente> agenteOp = agenteRepository.findById( agenteId );
        	if ( agenteOp.isEmpty() )
        		throw new BusinessException( Errors.AGENTE_NOT_FOUND );
        	
        	Agente agente = agenteOp.get();
        	empresa = agente.getEmpresa();
        	
        	dispositivo.setAgente( agente );        	        	
        } else {
        	Long empresaId = request.getEmpresaId();
        	Optional<Empresa> empresaOp = empresaRepository.findById( empresaId );
        	if ( empresaOp.isEmpty() )
        		throw new BusinessException( Errors.EMPRESA_NOT_FOUND );

        	empresa = empresaOp.get();
        	
        	dispositivo.setAgente( null ); 
        }
        
        Long empresaId = empresa.getId();
               
        int dispositivosQuantByEmpresa = dispositivoRepository.countByEmpresa( empresaId );
        if ( dispositivosQuantByEmpresa >= empresa.getMaxDispositivosQuant() )
        	throw new BusinessException( Errors.DISPOSITIVO_CREATE_EXCEDE_LIMITE, ""+dispositivosQuantByEmpresa );         

        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findByNomeAndEmpresa( nome, empresaId );
        if ( dispositivoOp.isPresent() )
            throw new BusinessException( Errors.DISPOSITIVO_ALREADY_EXISTS );
        
        dispositivo.setEmpresa( empresa );        

        dispositivoRepository.save( dispositivo );
        
        if ( !dispositivo.isMonitoradoPorAgente() )
        	dispositivoMonitorEscalonador.startMonitoramento( dispositivo.getId() ); 
    }

    public void updateDispositivo( Long dispositivoId, SaveDispositivoRequest request ) {
        request.validate();

        String nome = request.getNome();
        
        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
        if ( dispositivoOp.isEmpty() )
            throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );

        Dispositivo dispositivo = dispositivoOp.get();
        
        Empresa empresa;        
        if ( request.isMonitoradoPorAgente() ) {
        	Long agenteId = request.getAgenteId();        
        	Optional<Agente> agenteOp = agenteRepository.findById( agenteId );
        	if ( agenteOp.isEmpty() )
        		throw new BusinessException( Errors.AGENTE_NOT_FOUND );
        	
        	Agente agente = agenteOp.get();
        	empresa = agente.getEmpresa();
        	
        	dispositivo.setAgente( agente );
        	
        	if ( dispositivo.isSendoMonitorado() ) {
        		dispositivoMonitorEscalonador.stopMonitoramentoIgnoreResult( dispositivo.getId() );
        		dispositivo.setSendoMonitorado( false ); 
        	}
        } else {
        	Long empresaId = request.getEmpresaId();
        	Optional<Empresa> empresaOp = empresaRepository.findById( empresaId );
        	if ( empresaOp.isEmpty() )
        		throw new BusinessException( Errors.EMPRESA_NOT_FOUND );

        	empresa = empresaOp.get();
        	
        	dispositivo.setAgente( null ); 
        }
        
        Long empresaId = empresa.getId();
        dispositivo.setEmpresa( empresa ); 

        if ( !dispositivo.getNome().equalsIgnoreCase( nome ) )
            if ( dispositivoRepository.findByNomeAndEmpresa( nome, empresaId ).isPresent() )
                throw new BusinessException( Errors.DISPOSITIVO_ALREADY_EXISTS );

        dispositivoMapper.load( dispositivo, request );

        dispositivoRepository.save( dispositivo );
        
        dispositivo = dispositivoRepository.findById( dispositivo.getId() ).get();
        dispositivoMonitorEscalonador.updateDispositivoInMonitor( dispositivo );
    }
    
    public List<DispositivoResponse> listDispositivos( Long empresaId ) {    	
        List<Dispositivo> dispositivos = dispositivoRepository.list( empresaId );
        
        List<DispositivoResponse> responses = new ArrayList<>();
        for( Dispositivo dispositivo : dispositivos )
            responses.add( this.buildDispositivoResponse( dispositivo ) );
        return responses;
    }

    public DispositivoResponse getDispositivo( Long dispositivoId ) {
        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
        if ( dispositivoOp.isEmpty() )
            throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );

        Dispositivo dispositivo = dispositivoOp.get();
        return this.buildDispositivoResponse( dispositivo );
    }

    public void deleteDispositivo( Long dispositivoId ) {
        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
        if ( dispositivoOp.isEmpty() )
            throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );
        
        dispositivoMonitorEscalonador.stopMonitoramento( dispositivoId );

        dispositivoRepository.deleteById( dispositivoId );
    }

    private DispositivoResponse buildDispositivoResponse( Dispositivo dispositivo ) {
        DispositivoResponse resp = dispositivoMapper.map( dispositivo );
        Empresa empresa = dispositivo.getEmpresa();
        if ( empresa != null )
            resp.setEmpresa( empresaMapper.map( empresa ) );

        return resp;
    }

}