package com.redemonitor.service;

import com.redemonitor.dto.request.CreateUsuarioRequest;
import com.redemonitor.dto.request.UpdateUsuarioRequest;
import com.redemonitor.dto.response.UsuarioGrupoResponse;
import com.redemonitor.dto.response.UsuarioResponse;
import com.redemonitor.exception.BusinessException;
import com.redemonitor.exception.Errors;
import com.redemonitor.mapper.UsuarioGrupoMapper;
import com.redemonitor.mapper.UsuarioMapper;
import com.redemonitor.model.Usuario;
import com.redemonitor.model.UsuarioGrupo;
import com.redemonitor.model.UsuarioGrupoMap;
import com.redemonitor.repository.UsuarioGrupoMapRepository;
import com.redemonitor.repository.UsuarioGrupoRepository;
import com.redemonitor.repository.UsuarioRepository;
import com.redemonitor.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepository;

    @Autowired
    private UsuarioGrupoMapRepository usuarioGrupoMapRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioGrupoMapper usuarioGrupoMapper;

    @Autowired
    private HashUtil hashUtil;

    public void createUsuario( CreateUsuarioRequest request ) {
        request.validate();

        Usuario usuario = usuarioMapper.map( request );
        String username = usuario.getUsername();

        Optional<Usuario> usuarioOp = usuarioRepository.findByUsername( username );
        if ( usuarioOp.isPresent() )
            throw new BusinessException( Errors.USER_ALREADY_EXISTS );

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
                throw new BusinessException(Errors.USER_ALREADY_EXISTS);

        usuarioMapper.load( usuario, request );

        usuarioRepository.save( usuario );
    }

    public List<UsuarioResponse> filterUsuarios( String nomePart ) {
        List<Usuario> usuarios = usuarioRepository.filter( "%"+nomePart+"%" );
        return usuarios.stream().map( usuarioMapper::map ).toList();
    }

    public UsuarioResponse getUsuario( Long usuarioId ) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById( usuarioId );
        if ( usuarioOp.isEmpty() )
            throw new BusinessException( Errors.USER_NOT_FOUND );

        return usuarioOp.map( usuarioMapper::map ).orElseThrow();
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

}