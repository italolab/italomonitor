package com.redemonitor.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.redemonitor.main.apidoc.usuarioGrupo.*;
import com.redemonitor.main.dto.request.SaveUsuarioGrupoRequest;
import com.redemonitor.main.dto.response.RoleResponse;
import com.redemonitor.main.dto.response.UsuarioGrupoResponse;
import com.redemonitor.main.service.UsuarioGrupoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuario-grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioGrupoService usuarioGrupoService;

    @CreateUsuarioGrupoDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @PostMapping
    public ResponseEntity<String> createUsuarioGrupo(@RequestBody SaveUsuarioGrupoRequest request ) {
        usuarioGrupoService.createUsuarioGrupo( request );
        return ResponseEntity.ok( "Grupo de usuário registrado com sucesso." );
    }

    @UpdateUsuarioGrupoDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @PutMapping("/{usuarioGrupoId}")
    public ResponseEntity<String> updateUsuarioGrupo(@PathVariable Long usuarioGrupoId, @RequestBody SaveUsuarioGrupoRequest request ) {
        usuarioGrupoService.updateUsuarioGrupo( usuarioGrupoId, request );
        return ResponseEntity.ok( "Grupo de usuário alterado com sucesso." );
    }

    @FilterUsuarioGruposDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @GetMapping
    public ResponseEntity<List<UsuarioGrupoResponse>> filterUsuarioGrupos( @RequestParam("nomepart") String nomePart ) {
        List<UsuarioGrupoResponse> responses = usuarioGrupoService.filterUsuarioGrupos( nomePart );
        return ResponseEntity.ok( responses );
    }

    @GetUsuarioGrupoDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @GetMapping("/{usuarioGrupoId}/get")
    public ResponseEntity<UsuarioGrupoResponse> getUsuarioGrupo( @PathVariable Long usuarioGrupoId ) {
        UsuarioGrupoResponse resp = usuarioGrupoService.getUsuarioGrupo( usuarioGrupoId );
        return ResponseEntity.ok( resp );
    }

    @GetRolesByUsuarioGrupoIDDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @GetMapping("/{usuarioGrupoId}/roles")
    public ResponseEntity<List<RoleResponse>> getRolesByGrupoId( @PathVariable Long usuarioGrupoId ) {
        List<RoleResponse> roles = usuarioGrupoService.getRolesByGrupoId( usuarioGrupoId );
        return ResponseEntity.ok( roles );
    }

    @VinculaRoleGrupoDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @PostMapping("/{usuarioGrupoId}/roles/{roleId}")
    public ResponseEntity<String> vinculaRole( @PathVariable Long usuarioGrupoId, @PathVariable Long roleId ) {
        usuarioGrupoService.vinculaRole( usuarioGrupoId, roleId );
        return ResponseEntity.ok( "Role vinculado com sucesso ao grupo de usuário." );
    }

    @DeleteVinculoRoleGrupoDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @DeleteMapping("/{usuarioGrupoId}/roles/{roleId}")
    public ResponseEntity<String> removeRoleVinculado( @PathVariable Long usuarioGrupoId, @PathVariable Long roleId ) {
        usuarioGrupoService.removeRoleVinculado( usuarioGrupoId, roleId );
        return ResponseEntity.ok( "Vínculo grupo de usuário/role removido com sucesso." );
    }

    @DeleteUsuarioGrupoDoc
    @PreAuthorize("hasAuthority('usuario-all')")
    @DeleteMapping("/{usuarioGrupoId}")
    public ResponseEntity<String> deleteUsuarioGrupo( @PathVariable Long usuarioGrupoId ) {
        usuarioGrupoService.deleteUsuarioGrupo( usuarioGrupoId );
        return ResponseEntity.ok( "Usuário deletado com sucesso." );
    }

}
