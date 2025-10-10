package com.redemonitor.controller;

import com.redemonitor.controller.dto.request.UsuarioRequest;
import com.redemonitor.controller.dto.response.UsuarioResponse;
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

    @PreAuthorize("hasAuthority('usuario-write')")
    @PostMapping
    public ResponseEntity<String> createUsuario( @RequestBody UsuarioRequest request ) {
        usuarioService.createUsuario( request );
        return ResponseEntity.ok( "Usuario registrado com sucesso." );
    }

    @PreAuthorize("hasAuthority('usuario-write')")
    @PutMapping("/{usuarioId}")
    public ResponseEntity<String> updateUsuario( @PathVariable Long usuarioId, @RequestBody UsuarioRequest request ) {
        usuarioService.updateUsuario( usuarioId, request );
        return ResponseEntity.ok( "Usuario alterado com sucesso." );
    }

    @PreAuthorize("hasAuthority('usuario-read')")
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> filterUsuarios( @RequestParam("nomepart") String nomePart ) {
        List<UsuarioResponse> responses = usuarioService.filterUsuarios( nomePart );
        return ResponseEntity.ok( responses );
    }

    @PreAuthorize("hasAuthority('usuario-read')")
    @GetMapping("/get/{usuarioId}")
    public ResponseEntity<UsuarioResponse> getUsuario( @PathVariable Long usuarioId ) {
        UsuarioResponse resp = usuarioService.getUsuario( usuarioId );
        return ResponseEntity.ok( resp );
    }

    @PreAuthorize("hasAuthority('usuario-delete')")
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<String> deleteUsuario( @PathVariable Long usuarioId ) {
        usuarioService.deleteUsuario( usuarioId );
        return ResponseEntity.ok( "Usuário deletado com sucesso." );
    }

}
