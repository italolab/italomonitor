package com.redemonitor.controller;

import com.redemonitor.apidoc.usuario.GetRolesByUsuarioGrupoIDDoc;
import com.redemonitor.apidoc.usuarioGrupo.*;
import com.redemonitor.dto.request.SaveUsuarioGrupoRequest;
import com.redemonitor.dto.response.RoleResponse;
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
    @GetMapping("/{usuarioGrupoId}/get")
    public ResponseEntity<UsuarioGrupoResponse> getUsuarioGrupo( @PathVariable Long usuarioGrupoId ) {
        UsuarioGrupoResponse resp = usuarioGrupoService.getUsuarioGrupo( usuarioGrupoId );
        return ResponseEntity.ok( resp );
    }

    @GetRolesByUsuarioGrupoIDDoc
    @PreAuthorize("hasAuthority('role-read')")
    @GetMapping("/{usuarioGrupoId}/roles")
    public ResponseEntity<List<RoleResponse>> getGruposByUsuarioId( @PathVariable Long usuarioGrupoId ) {
        List<RoleResponse> roles = usuarioGrupoService.getRolesByGrupoId( usuarioGrupoId );
        return ResponseEntity.ok( roles );
    }

    @VinculaRoleGrupoDoc
    @PreAuthorize("hasAuthority('usuario-grupo-write')")
    @PostMapping("/{usuarioGrupoId}/roles/{roleId}")
    public ResponseEntity<String> vinculaRole( @PathVariable Long usuarioGrupoId, @PathVariable Long roleId ) {
        usuarioGrupoService.vinculaRole( usuarioGrupoId, roleId );
        return ResponseEntity.ok( "Role vinculado com sucesso ao grupo de usuário." );
    }

    @DeleteVinculoRoleGrupoDoc
    @PreAuthorize("hasAuthority('usuario-write')")
    @DeleteMapping("/{usuarioId}/grupos/{usuarioGrupoId}")
    public ResponseEntity<String> removeRoleVinculado( @PathVariable Long usuarioGrupoId, @PathVariable Long roleId ) {
        usuarioGrupoService.removeRoleVinculado( usuarioGrupoId, roleId );
        return ResponseEntity.ok( "Vínculo grupo de usuário/role removido com sucesso." );
    }

    @DeleteUsuarioGrupoDoc
    @PreAuthorize("hasAuthority('usuario-grupo-delete')")
    @DeleteMapping("/{usuarioGrupoId}")
    public ResponseEntity<String> deleteUsuarioGrupo( @PathVariable Long usuarioGrupoId ) {
        usuarioGrupoService.deleteUsuarioGrupo( usuarioGrupoId );
        return ResponseEntity.ok( "Usuário deletado com sucesso." );
    }

}
