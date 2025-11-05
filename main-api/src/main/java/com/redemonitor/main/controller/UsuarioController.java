package com.redemonitor.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redemonitor.main.apidoc.usuario.CreateUsuarioDoc;
import com.redemonitor.main.apidoc.usuario.DeleteUsuarioDoc;
import com.redemonitor.main.apidoc.usuario.DeleteVinculoUsuarioGrupoDoc;
import com.redemonitor.main.apidoc.usuario.FilterUsuariosDoc;
import com.redemonitor.main.apidoc.usuario.GetUsuarioDoc;
import com.redemonitor.main.apidoc.usuario.GetUsuarioGruposByUsuarioIDDoc;
import com.redemonitor.main.apidoc.usuario.UpdateUsuarioDoc;
import com.redemonitor.main.apidoc.usuario.VinculaUsuarioGrupoDoc;
import com.redemonitor.main.dto.request.CreateUsuarioRequest;
import com.redemonitor.main.dto.request.UpdateUsuarioRequest;
import com.redemonitor.main.dto.response.UsuarioGrupoResponse;
import com.redemonitor.main.dto.response.UsuarioResponse;
import com.redemonitor.main.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @CreateUsuarioDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @PostMapping
    public ResponseEntity<String> createUsuario( @RequestBody CreateUsuarioRequest request ) {
        usuarioService.createUsuario( request );
        return ResponseEntity.ok( "Usuario registrado com sucesso." );
    }

    @UpdateUsuarioDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @PutMapping("/{usuarioId}")
    public ResponseEntity<String> updateUsuario(@PathVariable Long usuarioId, @RequestBody UpdateUsuarioRequest request ) {
        usuarioService.updateUsuario( usuarioId, request );
        return ResponseEntity.ok( "Usuario alterado com sucesso." );
    }

    @FilterUsuariosDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> filterUsuarios( @RequestParam("nomepart") String nomePart ) {
        List<UsuarioResponse> responses = usuarioService.filterUsuarios( nomePart );
        return ResponseEntity.ok( responses );
    }

    @GetUsuarioDoc
    @PreAuthorize("hasAnyAuthority('usuario-all', 'usuario-get')")
    @GetMapping("/{usuarioId}/get")
    public ResponseEntity<UsuarioResponse> getUsuario( @PathVariable Long usuarioId ) {
        UsuarioResponse resp = usuarioService.getUsuario( usuarioId );
        return ResponseEntity.ok( resp );
    }

    @GetUsuarioGruposByUsuarioIDDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @GetMapping("/{usuarioId}/grupos")
    public ResponseEntity<List<UsuarioGrupoResponse>> getGruposByUsuarioId( @PathVariable Long usuarioId ) {
        List<UsuarioGrupoResponse> grupos = usuarioService.getGruposByUsuarioId( usuarioId );
        return ResponseEntity.ok( grupos );
    }

    @VinculaUsuarioGrupoDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @PostMapping("/{usuarioId}/grupos/{usuarioGrupoId}")
    public ResponseEntity<String> vinculaGrupo( @PathVariable Long usuarioId, @PathVariable Long usuarioGrupoId ) {
        usuarioService.vinculaGrupo( usuarioId, usuarioGrupoId );
        return ResponseEntity.ok( "Grupo vinculado com sucesso ao usuário." );
    }

    @DeleteVinculoUsuarioGrupoDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @DeleteMapping("/{usuarioId}/grupos/{usuarioGrupoId}")
    public ResponseEntity<String> removeGrupoVinculado( @PathVariable Long usuarioId, @PathVariable Long usuarioGrupoId ) {
        usuarioService.removeGrupoVinculado( usuarioId, usuarioGrupoId );
        return ResponseEntity.ok( "Vínculo usuário/grupo removido com sucesso." );
    }

    @DeleteUsuarioDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<String> deleteUsuario( @PathVariable Long usuarioId ) {
        usuarioService.deleteUsuario( usuarioId );
        return ResponseEntity.ok( "Usuário deletado com sucesso." );
    }

}
