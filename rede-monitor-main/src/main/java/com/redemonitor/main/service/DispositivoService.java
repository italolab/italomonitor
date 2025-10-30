package com.redemonitor.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redemonitor.main.dto.request.SaveDispositivoRequest;
import com.redemonitor.main.dto.response.DispositivoResponse;
import com.redemonitor.main.exception.BusinessException;
import com.redemonitor.main.exception.Errors;
import com.redemonitor.main.mapper.DispositivoMapper;
import com.redemonitor.main.mapper.EmpresaMapper;
import com.redemonitor.main.model.Dispositivo;
import com.redemonitor.main.model.Empresa;
import com.redemonitor.main.repository.DispositivoRepository;
import com.redemonitor.main.repository.EmpresaRepository;

@Service
public class DispositivoService {

    @Autowired
    private DispositivoMonitorService dispositivoMonitorService;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private DispositivoMapper dispositivoMapper;

    @Autowired
    private EmpresaMapper empresaMapper;

    public void createDispositivo( SaveDispositivoRequest request ) {
        request.validate();

        Dispositivo dispositivo = dispositivoMapper.map( request );
        String nome = dispositivo.getNome();

        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findByNome( nome );
        if ( dispositivoOp.isPresent() )
            throw new BusinessException( Errors.DISPOSITIVO_ALREADY_EXISTS );

        Optional<Empresa> empresaOp = empresaRepository.findById( request.getEmpresaId() );
        if ( empresaOp.isEmpty() )
            throw new BusinessException( Errors.EMPRESA_NOT_FOUND );

        dispositivo.setEmpresa( empresaOp.get() );


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

        Optional<Empresa> empresaOp = empresaRepository.findById( request.getEmpresaId() );
        if ( empresaOp.isEmpty() )
            throw new BusinessException( Errors.EMPRESA_NOT_FOUND );

        dispositivo.setEmpresa( empresaOp.get() );

        dispositivoMapper.load( dispositivo, request );

        dispositivoRepository.save( dispositivo );
        dispositivoMonitorService.setDeviceInMonitor( dispositivoId );
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

    public void deleteDispositivo( Long dispositivoId, String username ) {
        Optional<Dispositivo> dispositivoOp = dispositivoRepository.findById( dispositivoId );
        if ( dispositivoOp.isEmpty() )
            throw new BusinessException( Errors.DISPOSITIVO_NOT_FOUND );

        dispositivoMonitorService.stopMonitoramento( dispositivoId, username );
        
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