package com.redemonitor.controller;

import com.redemonitor.apidoc.usuarioGrupo.*;
import com.redemonitor.dto.request.SaveUsuarioGrupoRequest;
import com.redemonitor.dto.response.UsuarioGrupoResponse;
import com.redemonitor.service.UsuarioGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuario-grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioGrupoService usuarioGrupoService;

    @CreateUsuarioGrupoDoc
    @PreAuthorize("hasAuthority('usuario-grupo-write')")
    @PostMapping
    public ResponseEntity<String> createUsuarioGrupo(@RequestBody SaveUsuarioGrupoRequest request ) {
        usuarioGrupoService.createUsuarioGrupo( request );
        return ResponseEntity.ok( "Grupo de usuário registrado com sucesso." );
    }

    @UpdateUsuarioGrupoDoc
    @PreAuthorize("hasAuthority('usuario-grupo-write')")
    @PutMapping("/{usuarioGrupoId}")
    public ResponseEntity<String> updateUsuarioGrupo(@PathVariable Long usuarioGrupoId, @RequestBody SaveUsuarioGrupoRequest request ) {
        usuarioGrupoService.updateUsuarioGrupo( usuarioGrupoId, request );
        return ResponseEntity.ok( "Grupo de usuário alterado com sucesso." );
    }

    @FilterUsuarioGruposDoc
    @PreAuthorize("hasAuthority('usuario-grupo-read')")
    @GetMapping
    public ResponseEntity<List<UsuarioGrupoResponse>> filterUsuarioGrupos( @RequestParam("nomepart") String nomePart ) {
        List<UsuarioGrupoResponse> responses = usuarioGrupoService.filterUsuarioGrupos( nomePart );
        return ResponseEntity.ok( responses );
    }

    @GetUsuarioGrupoDoc
    @PreAuthorize("hasAuthority('usuario-grupo-read')")
    @GetMapping("/get/{usuarioGrupoId}")
    public ResponseEntity<UsuarioGrupoResponse> getUsuarioGrupo( @PathVariable Long usuarioGrupoId ) {
        UsuarioGrupoResponse resp = usuarioGrupoService.getUsuarioGrupo( usuarioGrupoId );
        return ResponseEntity.ok( resp );
    }

    @DeleteUsuarioGrupoDoc
    @PreAuthorize("hasAuthority('usuario-grupo-delete')")
    @DeleteMapping("/{usuarioGrupoId}")
    public ResponseEntity<String> deleteUsuarioGrupo( @PathVariable Long usuarioGrupoId ) {
        usuarioGrupoService.deleteUsuarioGrupo( usuarioGrupoId );
        return ResponseEntity.ok( "Usuário deletado com sucesso." );
    }

}
