package com.redemonitor.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.redemonitor.main.apidoc.empresa.*;
import com.redemonitor.main.dto.request.SaveEmpresaRequest;
import com.redemonitor.main.dto.response.EmpresaResponse;
import com.redemonitor.main.service.EmpresaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @CreateEmpresaDoc
    @PreAuthorize("hasAuthority('empresa-write')")
    @PostMapping
    public ResponseEntity<String> createEmpresa(@RequestBody SaveEmpresaRequest request ) {
        empresaService.createEmpresa( request );
        return ResponseEntity.ok( "Empresa registrada com sucesso." );
    }

    @UpdateEmpresaDoc
    @PreAuthorize("hasAuthority('empresa-write')")
    @PutMapping("/{empresaId}")
    public ResponseEntity<String> updateEmpresa( @PathVariable Long empresaId, @RequestBody SaveEmpresaRequest request ) {
        empresaService.updateEmpresa( empresaId, request );
        return ResponseEntity.ok( "Empresa alterada com sucesso." );
    }

    @FilterEmpresasDoc
    @PreAuthorize("hasAuthority('empresa-read')")
    @GetMapping
    public ResponseEntity<List<EmpresaResponse>> filterEmpresas(@RequestParam("nomepart") String nomePart ) {
        List<EmpresaResponse> responses = empresaService.filterEmpresas( nomePart );
        return ResponseEntity.ok( responses );
    }

    @GetEmpresaDoc
    @PreAuthorize("hasAuthority('empresa-read')")
    @GetMapping("/{empresaId}/get")
    public ResponseEntity<EmpresaResponse> getEmpresa( @PathVariable Long empresaId ) {
        EmpresaResponse resp = empresaService.getEmpresa( empresaId );
        return ResponseEntity.ok( resp );
    }

    @DeleteEmpresaDoc
    @PreAuthorize("hasAuthority('empresa-delete')")
    @DeleteMapping("/{empresaId}")
    public ResponseEntity<String> deleteEmpresa( @PathVariable Long empresaId ) {
        empresaService.deleteEmpresa( empresaId );
        return ResponseEntity.ok( "Empresa deletada com sucesso." );
    }

}


