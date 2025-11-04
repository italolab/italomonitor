package com.redemonitor.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.redemonitor.main.apidoc.empresa.*;
import com.redemonitor.main.dto.request.SaveEmpresaRequest;
import com.redemonitor.main.dto.response.EmpresaResponse;
import com.redemonitor.main.service.AuthorizationService;
import com.redemonitor.main.service.EmpresaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;
    
    @Autowired
    private AuthorizationService authorizationService;

    @CreateEmpresaDoc
    @PreAuthorize("hasAuthority('empresa-all')")
    @PostMapping
    public ResponseEntity<String> createEmpresa(@RequestBody SaveEmpresaRequest request ) {
        empresaService.createEmpresa( request );
        return ResponseEntity.ok( "Empresa registrada com sucesso." );
    }

    @UpdateEmpresaDoc
    @PreAuthorize("hasAuthority('empresa-all')")
    @PutMapping("/{empresaId}")
    public ResponseEntity<String> updateEmpresa( @PathVariable Long empresaId, @RequestBody SaveEmpresaRequest request ) {
        empresaService.updateEmpresa( empresaId, request );
        return ResponseEntity.ok( "Empresa alterada com sucesso." );
    }

    @FilterEmpresasDoc
    @PreAuthorize("hasAuthority('empresa-all')")
    @GetMapping
    public ResponseEntity<List<EmpresaResponse>> filterEmpresas(@RequestParam("nomepart") String nomePart ) {
        List<EmpresaResponse> responses = empresaService.filterEmpresas( nomePart );
        return ResponseEntity.ok( responses );
    }

    @GetEmpresaDoc
    @PreAuthorize("hasAnyAuthority('empresa-all', 'empresa-get')")
    @GetMapping("/{empresaId}/get")
    public ResponseEntity<EmpresaResponse> getEmpresa( 
    		@PathVariable Long empresaId, 
    		@RequestHeader( "Authorization" ) String authorizationHeader ) {
    	
    	authorizationService.authorizeByEmpresa( empresaId, authorizationHeader );
    	
        EmpresaResponse resp = empresaService.getEmpresa( empresaId );
        return ResponseEntity.ok( resp );
    }

    @DeleteEmpresaDoc
    @PreAuthorize("hasAuthority('empresa-all')")
    @DeleteMapping("/{empresaId}")
    public ResponseEntity<String> deleteEmpresa( @PathVariable Long empresaId ) {
        empresaService.deleteEmpresa( empresaId );
        return ResponseEntity.ok( "Empresa deletada com sucesso." );
    }

}


