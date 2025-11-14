package com.italomonitor.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.italomonitor.main.components.DispositivoMonitorEscalonador;
import com.italomonitor.main.dto.request.NoAdminSaveEmpresaRequest;
import com.italomonitor.main.dto.request.SaveEmpresaRequest;
import com.italomonitor.main.dto.response.EmpresaResponse;
import com.italomonitor.main.exception.BusinessException;
import com.italomonitor.main.exception.Errors;
import com.italomonitor.main.mapper.EmpresaMapper;
import com.italomonitor.main.model.Empresa;
import com.italomonitor.main.repository.EmpresaRepository;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaMapper empresaMapper;
    
    @Autowired
    private DispositivoMonitorEscalonador dispositivoMonitorEscalonador;

    public void createEmpresa( SaveEmpresaRequest request ) {
        request.validate();

        Empresa empresa = empresaMapper.map( request );
        String nome = empresa.getNome();

        Optional<Empresa> empresaOp = empresaRepository.findByNome( nome );
        if ( empresaOp.isPresent() )
            throw new BusinessException( Errors.EMPRESA_ALREADY_EXISTS );

        empresaRepository.save( empresa );
    }

    public void updateEmpresa( Long id, SaveEmpresaRequest request ) {
        request.validate();

        String nome = request.getNome();

        Optional<Empresa> empresaOp = empresaRepository.findById( id );
        if ( empresaOp.isEmpty() )
            throw new BusinessException( Errors.EMPRESA_NOT_FOUND );

        Empresa empresa = empresaOp.get();
        if ( !empresa.getNome().equalsIgnoreCase( nome ) )
            if ( empresaRepository.findByNome( nome ).isPresent() )
                throw new BusinessException( Errors.EMPRESA_ALREADY_EXISTS );

        empresaMapper.load( empresa, request );

        empresaRepository.save( empresa );
    }
    
    public void noAdminUpdateEmpresa( Long id, NoAdminSaveEmpresaRequest request ) {
        request.validate();

        String nome = request.getNome();

        Optional<Empresa> empresaOp = empresaRepository.findById( id );
        if ( empresaOp.isEmpty() )
            throw new BusinessException( Errors.EMPRESA_NOT_FOUND );

        Empresa empresa = empresaOp.get();
        if ( !empresa.getNome().equalsIgnoreCase( nome ) )
            if ( empresaRepository.findByNome( nome ).isPresent() )
                throw new BusinessException( Errors.EMPRESA_ALREADY_EXISTS );

        empresaMapper.load( empresa, request );

        empresaRepository.save( empresa );
    }

    public List<EmpresaResponse> filterEmpresas( String nomePart ) {
        List<Empresa> empresas = empresaRepository.filter( "%"+nomePart+"%" );
        return empresas.stream().map( empresaMapper::map ).toList();
    }

    public EmpresaResponse getEmpresa( Long id ) {
        Optional<Empresa> empresaOp = empresaRepository.findById( id );
        if ( empresaOp.isEmpty() )
            throw new BusinessException( Errors.EMPRESA_NOT_FOUND );

        return empresaOp.map( empresaMapper::map ).orElseThrow();
    }

    public void deleteEmpresa( Long id ) {
        Optional<Empresa> empresaOp = empresaRepository.findById( id );
        if ( empresaOp.isEmpty() )
            throw new BusinessException( Errors.EMPRESA_NOT_FOUND );
        
        dispositivoMonitorEscalonador.stopEmpresaMonitoramentos( id );

        empresaRepository.deleteById( id );      
    }

}
