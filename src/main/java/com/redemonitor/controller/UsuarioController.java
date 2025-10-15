package com.redemonitor.controller;

import com.redemonitor.apidoc.usuario.*;
import com.redemonitor.dto.request.CreateUsuarioRequest;
import com.redemonitor.dto.request.UpdateUsuarioRequest;
import com.redemonitor.dto.response.UsuarioGrupoResponse;
import com.redemonitor.dto.response.UsuarioResponse;
import com.redemonitor.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasAuthority('usuario-read')")
    @GetMapping("/teste")
    public String teste() {
        return "Funcionou...";
    }

    @CreateUsuarioDoc
    @PreAuthorize("hasAuthority('usuario-write')")
    @PostMapping
    public ResponseEntity<String> createUsuario( @RequestBody CreateUsuarioRequest request ) {
        usuarioService.createUsuario( request );
        return ResponseEntity.ok( "Usuario registrado com sucesso." );
    }

    @UpdateUsuarioDoc
    @PreAuthorize("hasAuthority('usuario-write')")
    @PutMapping("/{usuarioId}")
    public ResponseEntity<String> updateUsuario(@PathVariable Long usuarioId, @RequestBody UpdateUsuarioRequest request ) {
        usuarioService.updateUsuario( usuarioId, request );
        return ResponseEntity.ok( "Usuario alterado com sucesso." );
    }

    @FilterUsuariosDoc
    @PreAuthorize("hasAuthority('usuario-read')")
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> filterUsuarios( @RequestParam("nomepart") String nomePart ) {
        List<UsuarioResponse> responses = usuarioService.filterUsuarios( nomePart );
        return ResponseEntity.ok( responses );
    }

    @GetUsuarioDoc
    @PreAuthorize("hasAuthority('usuario-read')")
    @GetMapping("/{usuarioId}/get")
    public ResponseEntity<UsuarioResponse> getUsuario( @PathVariable Long usuarioId ) {
        UsuarioResponse resp = usuarioService.getUsuario( usuarioId );
        return ResponseEntity.ok( resp );
    }

    @GetUsuarioGruposByUsuarioIDDoc
    @PreAuthorize("hasAuthority('usuario-grupo-read')")
    @GetMapping("/{usuarioId}/usuario-grupos")
    public ResponseEntity<List<UsuarioGrupoResponse>> getGruposByUsuarioId( @PathVariable Long usuarioId ) {
        List<UsuarioGrupoResponse> grupos = usuarioService.getGruposByUsuarioId( usuarioId );
        return ResponseEntity.ok( grupos );
    }

    @DeleteUsuarioDoc
    @PreAuthorize("hasAuthority('usuario-delete')")
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<String> deleteUsuario( @PathVariable Long usuarioId ) {
        usuarioService.deleteUsuario( usuarioId );
        return ResponseEntity.ok( "Usuário deletado com sucesso." );
    }

}
