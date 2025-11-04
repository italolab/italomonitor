package com.redemonitor.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redemonitor.main.components.util.HashUtil;
import com.redemonitor.main.dto.request.CreateUsuarioRequest;
import com.redemonitor.main.dto.request.UpdateUsuarioRequest;
import com.redemonitor.main.dto.response.UsuarioGrupoResponse;
import com.redemonitor.main.dto.response.UsuarioResponse;
import com.redemonitor.main.exception.BusinessException;
import com.redemonitor.main.exception.Errors;
import com.redemonitor.main.mapper.EmpresaMapper;
import com.redemonitor.main.mapper.UsuarioGrupoMapper;
import com.redemonitor.main.mapper.UsuarioMapper;
import com.redemonitor.main.model.Empresa;
import com.redemonitor.main.model.Usuario;
import com.redemonitor.main.model.UsuarioGrupo;
import com.redemonitor.main.model.UsuarioGrupoMap;
import com.redemonitor.main.repository.EmpresaRepository;
import com.redemonitor.main.repository.UsuarioGrupoMapRepository;
import com.redemonitor.main.repository.UsuarioGrupoRepository;
import com.redemonitor.main.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepository;

    @Autowired
    private UsuarioGrupoMapRepository usuarioGrupoMapRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioGrupoMapper usuarioGrupoMapper;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Autowired
    private HashUtil hashUtil;
    
    public void createUsuario( CreateUsuarioRequest request ) {
        request.validate();

        Usuario usuario = usuarioMapper.map( request );
        usuario.setSenha( hashUtil.geraHash( usuario.getSenha() ) ); 
        
        String username = usuario.getUsername();

        Optional<Usuario> usuarioOp = usuarioRepository.findByUsername( username );
        if ( usuarioOp.isPresent() )
            throw new BusinessException( Errors.USER_ALREADY_EXISTS );

        if ( request.getEmpresaId() != null ) {
            if ( request.getEmpresaId() == -1 ) {
                usuario.setEmpresa( null );
            } else {
                Optional<Empresa> empresaOp = empresaRepository.findById( request.getEmpresaId() );
                if ( empresaOp.isEmpty() )
                    throw new BusinessException( Errors.EMPRESA_NOT_FOUND );

                usuario.setEmpresa( empresaOp.get() );
            }
        }

        usuarioRepository.save( usuario );
    }

    public void updateUsuario( Long usuarioId, UpdateUsuarioRequest request ) {
        request.validate();

        String username = request.getUsername();

        Optional<Usuario> usuarioOp = usuarioRepository.findById( usuarioId );
        if ( usuarioOp.isEmpty() )
            throw new BusinessException( Errors.USER_NOT_FOUND );

        Usuario usuario = usuarioOp.get();
        if ( !usuario.getUsername().equalsIgnoreCase( username ) )
            if ( usuarioRepository.findByUsername( username ).isPresent() )
                throw new BusinessException( Errors.USER_ALREADY_EXISTS );

        if ( request.getEmpresaId() != null ) {
            if ( request.getEmpresaId() == -1 ) {
                usuario.setEmpresa( null );
            } else {
                Optional<Empresa> empresaOp = empresaRepository.findById( request.getEmpresaId() );
                if ( empresaOp.isEmpty() )
                    throw new BusinessException( Errors.EMPRESA_NOT_FOUND );

                usuario.setEmpresa( empresaOp.get() );
            }
        }

        usuarioMapper.load( usuario, request );
        usuarioRepository.save( usuario );
    }

    public List<UsuarioResponse> filterUsuarios( String nomePart ) {
        List<Usuario> usuarios = usuarioRepository.filter( "%"+nomePart+"%" );
        List<UsuarioResponse> responses = new ArrayList<>();
        for( Usuario usuario : usuarios )
            responses.add( this.buildUsuarioResponse( usuario ) );
        return responses;
    }

    public UsuarioResponse getUsuario( Long usuarioId ) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById( usuarioId );
        if ( usuarioOp.isEmpty() )
            throw new BusinessException( Errors.USER_NOT_FOUND );

        return this.buildUsuarioResponse( usuarioOp.get() );
    }

    public void vinculaGrupo( Long usuarioId, Long usuarioGrupoId ) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById( usuarioId );
        if ( usuarioOp.isEmpty() )
            throw new BusinessException( Errors.USER_NOT_FOUND );

        Optional<UsuarioGrupo> usuarioGrupoOp = usuarioGrupoRepository.findById( usuarioGrupoId );
        if ( usuarioGrupoOp.isEmpty() )
            throw new BusinessException( Errors.USER_GROUP_NOT_FOUND );

        UsuarioGrupoMap map = UsuarioGrupoMap.builder()
                .usuario( usuarioOp.get() )
                .usuarioGrupo( usuarioGrupoOp.get() )
                .build();

        usuarioGrupoMapRepository.save( map );
    }

    public void removeGrupoVinculado( Long usuarioId, Long usuarioGrupoId ) {
        Optional<UsuarioGrupoMap> mapOp = usuarioGrupoMapRepository.get( usuarioId, usuarioGrupoId );
        if ( mapOp.isEmpty() )
            throw new BusinessException( Errors.LINK_USER_GROUP_NOT_FOUND );

        Long vinculoId = mapOp.get().getId();

        usuarioGrupoMapRepository.deleteById( vinculoId );
    }

    public List<UsuarioGrupoResponse> getGruposByUsuarioId( Long usuarioId ) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById( usuarioId );
        if ( usuarioOp.isEmpty() )
            throw new BusinessException( Errors.USER_NOT_FOUND );

        Usuario usuario = usuarioOp.get();

        List<UsuarioGrupoResponse> grupos = new ArrayList<>();
        for( UsuarioGrupoMap map : usuario.getGrupos() )
            grupos.add( usuarioGrupoMapper.map( map.getUsuarioGrupo() ) );

        return grupos;
    }

    public void deleteUsuario( Long usuarioId ) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById( usuarioId );
        if ( usuarioOp.isEmpty() )
            throw new BusinessException( Errors.USER_NOT_FOUND );

        usuarioRepository.deleteById( usuarioId );
    }

    private UsuarioResponse buildUsuarioResponse(Usuario usuario ) {
        UsuarioResponse resp = usuarioMapper.map( usuario );

        Empresa empresa = usuario.getEmpresa();
        if ( empresa != null )
            resp.setEmpresa( empresaMapper.map( empresa ) );

        List<UsuarioGrupoResponse> grupos = new ArrayList<>();
        List<UsuarioGrupoMap> grupoMaps = usuario.getGrupos();
        for( UsuarioGrupoMap map : grupoMaps )
            grupos.add( usuarioGrupoMapper.map( map.getUsuarioGrupo() ) );
        resp.setGrupos( grupos );

        return resp;
    }

}