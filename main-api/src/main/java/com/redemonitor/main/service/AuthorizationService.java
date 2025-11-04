package com.redemonitor.main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redemonitor.main.components.util.JwtTokenUtil;
import com.redemonitor.main.components.util.JwtTokenUtil.JWTInfos;
import com.redemonitor.main.enums.UsuarioPerfil;
import com.redemonitor.main.exception.BusinessException;
import com.redemonitor.main.exception.Errors;
import com.redemonitor.main.repository.DispositivoRepository;
import com.redemonitor.main.repository.EventoRepository;

@Service
public class AuthorizationService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private DispositivoRepository dispositivoRepository;
    
    @Autowired
    private EventoRepository eventoRepository;
    
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
    		if ( empresaIdOp.isPresent() ) {
    			Long empresaId = empresaIdOp.get();
    			if ( empresaId != infos.getEmpresaId() )
    				throw new BusinessException( Errors.NOT_AUTHORIZED );
    		}    			
    	}
    }
        
    public void authorizeEventoOperByEmpresa( Long eventoId, String authorizationHeader ) {
    	JWTInfos infos = jwtTokenUtil.extractInfos( authorizationHeader );
    	if ( !infos.getPerfil().equals( UsuarioPerfil.ADMIN.name() ) ) {
    		Optional<Long> empresaIdOp = eventoRepository.getEmpresaId( eventoId );
    		if ( empresaIdOp.isPresent() ) {
    			Long empresaId = empresaIdOp.get();
    			if ( empresaId != infos.getEmpresaId() )
    				throw new BusinessException( Errors.NOT_AUTHORIZED );
    		}    			
    	}
    }
    
}
