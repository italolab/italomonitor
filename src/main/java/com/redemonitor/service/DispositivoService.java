package com.redemonitor.service;

import com.redemonitor.dto.request.SaveDispositivoRequest;
import com.redemonitor.dto.response.DispositivoResponse;
import com.redemonitor.exception.BusinessException;
import com.redemonitor.exception.Errors;
import com.redemonitor.mapper.EmpresaMapper;
import com.redemonitor.mapper.DispositivoMapper;
import com.redemonitor.model.Empresa;
import com.redemonitor.model.Dispositivo;
import com.redemonitor.repository.EmpresaRepository;
import com.redemonitor.repository.DispositivoRepository;
import com.redemonitor.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DispositivoService {

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private DispositivoMapper dispositivoMapper;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Autowired
    private HashUtil hashUtil;

    public void createDispositivo( SaveDispositivoRequest request ) {
        request.validate();

        Dispositivo dispositivo = dispositivoMapper.map( request );
        String nome = dispositivo.getNome();

        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findByNome( nome );
        if ( dispositivoOp.isPresent() )
            throw new BusinessException( Errors.DISPOSITIVO_ALREADY_EXISTS );

        if ( request.getEmpresaId() != null ) {
            if ( request.getEmpresaId() == -1 ) {
                dispositivo.setEmpresa( null );
            } else {
                Optional<Empresa> empresaOp = empresaRepository.findById( request.getEmpresaId() );
                if ( empresaOp.isEmpty() )
                    throw new BusinessException( Errors.EMPRESA_NOT_FOUND );

                dispositivo.setEmpresa( empresaOp.get() );
            }
        }

        dispositivoRepository.save( dispositivo );
    }

    public void updateDispositivo( Long dispositivoId, SaveDispositivoRequest request ) {
        request.validate();

        String nome = request.getNome();

        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
        if ( dispositivoOp.isEmpty() )
            throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );

        Dispositivo dispositivo = dispositivoOp.get();
        if ( !dispositivo.getNome().equalsIgnoreCase( nome ) )
            if ( dispositivoRepository.findByNome( nome ).isPresent() )
                throw new BusinessException( Errors.DISPOSITIVO_ALREADY_EXISTS );

        if ( request.getEmpresaId() != null ) {
            if ( request.getEmpresaId() == -1 ) {
                dispositivo.setEmpresa( null );
            } else {
                Optional<Empresa> empresaOp = empresaRepository.findById( request.getEmpresaId() );
                if ( empresaOp.isEmpty() )
                    throw new BusinessException( Errors.EMPRESA_NOT_FOUND );

                dispositivo.setEmpresa( empresaOp.get() );
            }
        }

        dispositivoMapper.load( dispositivo, request );

        dispositivoRepository.save( dispositivo );
    }

    public List<DispositivoResponse> filterDispositivos( String hostPart, String nomePart, String localPart ) {
        List<Dispositivo> dispositivos = dispositivoRepository.filter( 
                "%"+hostPart+"%",
                "%"+nomePart+"%",
                "%"+localPart+"%" );
        
        List<DispositivoResponse> responses = new ArrayList<>();
        for( Dispositivo dispositivo : dispositivos )
            responses.add( this.buildDispositivoResponse( dispositivo ) );
        return responses;
    }

    public DispositivoResponse getDispositivo( Long dispositivoId ) {
        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
        if ( dispositivoOp.isEmpty() )
            throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );

        return this.buildDispositivoResponse( dispositivoOp.get() );
    }

    public void deleteDispositivo( Long dispositivoId ) {
        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
        if ( dispositivoOp.isEmpty() )
            throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );

        dispositivoRepository.deleteById( dispositivoId );
    }

    private DispositivoResponse buildDispositivoResponse(Dispositivo dispositivo ) {
        DispositivoResponse resp = dispositivoMapper.map( dispositivo );

        Empresa empresa = dispositivo.getEmpresa();
        if ( empresa != null )
            resp.setEmpresa( empresaMapper.map( empresa ) );

        return resp;
    }

}