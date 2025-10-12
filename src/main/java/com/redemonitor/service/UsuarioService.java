package com.redemonitor.service;

import com.redemonitor.controller.dto.request.CreateUsuarioRequest;
import com.redemonitor.controller.dto.request.UpdateUsuarioRequest;
import com.redemonitor.controller.dto.response.UsuarioResponse;
import com.redemonitor.exception.ErrorException;
import com.redemonitor.exception.Errors;
import com.redemonitor.mapper.UsuarioMapper;
import com.redemonitor.model.Usuario;
import com.redemonitor.repository.UsuarioRepository;
import com.redemonitor.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private HashUtil hashUtil;

    public void createUsuario( CreateUsuarioRequest request ) {
        Usuario usuario = usuarioMapper.map( request );
        String username = usuario.getUsername();

        Optional<Usuario> usuarioOp = usuarioRepository.findByUsername( username );
        if ( usuarioOp.isPresent() )
            throw new ErrorException( Errors.USER_ALREADY_EXISTS );

        usuarioRepository.save( usuario );
    }

    public void updateUsuario( Long id, UpdateUsuarioRequest request ) {
        String username = request.getUsername();

        Optional<Usuario> usuarioOp = usuarioRepository.findById( id );
        if ( usuarioOp.isEmpty() )
            throw new ErrorException( Errors.USER_NOT_FOUND );

        Usuario usuario = usuarioOp.get();
        if ( usuario.getUsername().equalsIgnoreCase( username ) )
            throw new ErrorException( Errors.USER_ALREADY_EXISTS );

        usuarioMapper.load( usuario, request );

        usuarioRepository.save( usuario );
    }

    public List<UsuarioResponse> filterUsuarios( String nomePart ) {
        List<Usuario> usuarios = usuarioRepository.filter( "%"+nomePart+"%" );
        return usuarios.stream().map( usuarioMapper::map ).toList();
    }

    public UsuarioResponse getUsuario( Long id ) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById( id );
        if ( usuarioOp.isEmpty() )
            throw new ErrorException( Errors.USER_NOT_FOUND );

        return usuarioOp.map( usuarioMapper::map ).orElseThrow();
    }

    public void deleteUsuario( Long id ) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById( id );
        if ( usuarioOp.isEmpty() )
            throw new ErrorException( Errors.USER_NOT_FOUND );

        usuarioRepository.deleteById( id );
    }

}