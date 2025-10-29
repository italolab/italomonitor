package com.redemonitor.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.redemonitor.main.apidoc.usuario.*;
import com.redemonitor.main.dto.request.CreateUsuarioRequest;
import com.redemonitor.main.dto.request.UpdateUsuarioRequest;
import com.redemonitor.main.dto.response.UsuarioGrupoResponse;
import com.redemonitor.main.dto.response.UsuarioResponse;
import com.redemonitor.main.service.UsuarioService;

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
    @GetMapping("/{usuarioId}/grupos")
    public ResponseEntity<List<UsuarioGrupoResponse>> getGruposByUsuarioId( @PathVariable Long usuarioId ) {
        List<UsuarioGrupoResponse> grupos = usuarioService.getGruposByUsuarioId( usuarioId );
        return ResponseEntity.ok( grupos );
    }

    @VinculaUsuarioGrupoDoc
    @PreAuthorize("hasAuthority('usuario-write')")
    @PostMapping("/{usuarioId}/grupos/{usuarioGrupoId}")
    public ResponseEntity<String> vinculaGrupo( @PathVariable Long usuarioId, @PathVariable Long usuarioGrupoId ) {
        usuarioService.vinculaGrupo( usuarioId, usuarioGrupoId );
        return ResponseEntity.ok( "Grupo vinculado com sucesso ao usuário." );
    }

    @DeleteVinculoUsuarioGrupoDoc
    @PreAuthorize("hasAuthority('usuario-write')")
    @DeleteMapping("/{usuarioId}/grupos/{usuarioGrupoId}")
    public ResponseEntity<String> removeGrupoVinculado( @PathVariable Long usuarioId, @PathVariable Long usuarioGrupoId ) {
        usuarioService.removeGrupoVinculado( usuarioId, usuarioGrupoId );
        return ResponseEntity.ok( "Vínculo usuário/grupo removido com sucesso." );
    }

    @DeleteUsuarioDoc
    @PreAuthorize("hasAuthority('usuario-delete')")
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<String> deleteUsuario( @PathVariable Long usuarioId ) {
        usuarioService.deleteUsuario( usuarioId );
        return ResponseEntity.ok( "Usuário deletado com sucesso." );
    }

}
