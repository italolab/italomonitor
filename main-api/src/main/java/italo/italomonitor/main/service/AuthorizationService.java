package italo.italomonitor.main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import italo.italomonitor.main.components.util.JwtTokenUtil;
import italo.italomonitor.main.components.util.JwtTokenUtil.JWTInfos;
import italo.italomonitor.main.enums.UsuarioPerfil;
import italo.italomonitor.main.exception.BusinessException;
import italo.italomonitor.main.exception.Errors;
import italo.italomonitor.main.repository.AgenteRepository;
import italo.italomonitor.main.repository.DispositivoRepository;
import italo.italomonitor.main.repository.EventoRepository;

@Service
public class AuthorizationService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private AgenteRepository agenteRepository;
    
    @Autowired
    private DispositivoRepository dispositivoRepository;
    
    @Autowired
    private EventoRepository eventoRepository;
    
    public void authorizeByUsuario( Long usuarioId, String authorizationHeader ) {
    	JWTInfos infos = jwtTokenUtil.extractInfos( authorizationHeader );
    	if ( !infos.getPerfil().equals( UsuarioPerfil.ADMIN.name() ) )
    		if ( usuarioId != infos.getUsuarioId() )
    			throw new BusinessException( Errors.NOT_AUTHORIZED );
    		
    }
    
    public void authorizeByEmpresa( Long empresaId, String authorizationHeader ) {    
    	JWTInfos infos = jwtTokenUtil.extractInfos( authorizationHeader );
    	if ( !infos.getPerfil().equals( UsuarioPerfil.ADMIN.name() ) )
    		if ( empresaId != infos.getEmpresaId() )
    			throw new BusinessException( Errors.NOT_AUTHORIZED );    	
    }
    
    public void authorizeDispositivoOperByEmpresa( Long dispositivoId, String authorizationHeader ) {
    	JWTInfos infos = jwtTokenUtil.extractInfos( authorizationHeader );
    	if ( !infos.getPerfil().equals( UsuarioPerfil.ADMIN.name() ) ) {
    		Optional<Long> empresaIdOp = dispositivoRepository.getEmpresaId( dispositivoId );
    		if ( empresaIdOp.isEmpty() )
    			throw new BusinessException( Errors.EMPRESA_NOT_FOUND );
    		
			Long empresaId = empresaIdOp.get();
			if ( empresaId != infos.getEmpresaId() )
				throw new BusinessException( Errors.NOT_AUTHORIZED );    		    			
    	}
    }
    
    public void authorizeAgenteOperByEmpresa( Long agenteId, String authorizationHeader ) {
    	JWTInfos infos = jwtTokenUtil.extractInfos( authorizationHeader );
    	if ( !infos.getPerfil().equals( UsuarioPerfil.ADMIN.name() ) ) {
    		Optional<Long> empresaIdOp = agenteRepository.getEmpresaId( agenteId );
    		if ( empresaIdOp.isEmpty() )
    			throw new BusinessException( Errors.EMPRESA_NOT_FOUND );
    		
			Long empresaId = empresaIdOp.get();
			if ( empresaId != infos.getEmpresaId() )
				throw new BusinessException( Errors.NOT_AUTHORIZED );
    	}
    }
        
    public void authorizeEventoOperByEmpresa( Long eventoId, String authorizationHeader ) {
    	JWTInfos infos = jwtTokenUtil.extractInfos( authorizationHeader );
    	if ( !infos.getPerfil().equals( UsuarioPerfil.ADMIN.name() ) ) {
    		Optional<Long> empresaIdOp = eventoRepository.getEmpresaId( eventoId );
    		if ( empresaIdOp.isEmpty() )
    			throw new BusinessException( Errors.EMPRESA_NOT_FOUND );
    		
			Long empresaId = empresaIdOp.get();
			if ( empresaId != infos.getEmpresaId() )
				throw new BusinessException( Errors.NOT_AUTHORIZED );    			
    	}
    }
    
}
