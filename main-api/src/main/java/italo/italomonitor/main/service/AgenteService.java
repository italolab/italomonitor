package italo.italomonitor.main.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import italo.italomonitor.main.dto.request.SaveAgenteRequest;
import italo.italomonitor.main.dto.response.AgenteResponse;
import italo.italomonitor.main.exception.BusinessException;
import italo.italomonitor.main.exception.Errors;
import italo.italomonitor.main.mapper.AgenteMapper;
import italo.italomonitor.main.model.Agente;
import italo.italomonitor.main.model.Empresa;
import italo.italomonitor.main.repository.AgenteRepository;
import italo.italomonitor.main.repository.EmpresaRepository;

@Service
public class AgenteService {

    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private AgenteMapper agenteMapper;

    public AgenteResponse createAgente( SaveAgenteRequest request, Long empresaId ) {
        request.validate();

        Agente agente = agenteMapper.map( request );
        String nome = agente.getNome();        

        Optional<Agente> agenteOp = agenteRepository.findByNomeAndEmpresa( nome, empresaId );
        if ( agenteOp.isPresent() )
            throw new BusinessException( Errors.AGENTE_ALREADY_EXISTS );
        
        Optional<Empresa> empresaOp = empresaRepository.findById( empresaId );
        if ( empresaOp.isEmpty() )
        	throw new BusinessException( Errors.EMPRESA_NOT_FOUND );
        
        Empresa empresa = empresaOp.get();
        
        agente.setChave( UUID.randomUUID().toString() ); 
        agente.setEmpresa( empresa );
        agenteRepository.save( agente );
        
        return agenteMapper.map( agente );
    }

    public void updateAgente( Long agenteId, SaveAgenteRequest request, Long empresaId ) {
        request.validate();

        String nome = request.getNome();

        Optional<Agente> agenteOp = agenteRepository.findById( agenteId );
        if ( agenteOp.isEmpty() )
            throw new BusinessException( Errors.AGENTE_NOT_FOUND );

        Agente agente = agenteOp.get();
        if ( !agente.getNome().equalsIgnoreCase( nome ) )
            if ( agenteRepository.findByNomeAndEmpresa( nome, empresaId ).isPresent() )
                throw new BusinessException( Errors.AGENTE_ALREADY_EXISTS);

        agenteMapper.load( agente, request );

        agenteRepository.save( agente );
    }

    public List<AgenteResponse> filterAgentes( String nomePart, Long empresaId ) {
        List<Agente> agentes = agenteRepository.filter( "%"+nomePart+"%", empresaId );
        return agentes.stream().map( agenteMapper::map ).toList();
    }

    public AgenteResponse getAgente( Long id ) {
        Optional<Agente> agenteOp = agenteRepository.findById( id );
        if ( agenteOp.isEmpty() )
            throw new BusinessException( Errors.AGENTE_NOT_FOUND );

        return agenteOp.map( agenteMapper::map ).orElseThrow();
    }

    public void deleteAgente( Long id ) {
        Optional<Agente> agenteOp = agenteRepository.findById( id );
        if ( agenteOp.isEmpty() )
            throw new BusinessException( Errors.AGENTE_NOT_FOUND );

        agenteRepository.deleteById( id );
    }

}
