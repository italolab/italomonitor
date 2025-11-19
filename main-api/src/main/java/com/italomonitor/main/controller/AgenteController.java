package com.italomonitor.main.controller;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.italomonitor.main.apidoc.agente.CreateAgenteDoc;
import com.italomonitor.main.apidoc.agente.DeleteAgenteDoc;
import com.italomonitor.main.apidoc.agente.FilterAgentesDoc;
import com.italomonitor.main.apidoc.agente.GetAgenteDoc;
import com.italomonitor.main.apidoc.agente.UpdateAgenteDoc;
import com.italomonitor.main.dto.request.SaveAgenteRequest;
import com.italomonitor.main.dto.response.AgenteResponse;
import com.italomonitor.main.service.AgenteService;
import com.italomonitor.main.service.AuthorizationService;

@RestController
@RequestMapping("/api/v1/agentes")
public class AgenteController {

    @Autowired
    private AgenteService agenteService;
    
    @Autowired
    private AuthorizationService authorizationService;
    
    @CreateAgenteDoc
    @PreAuthorize("hasAuthority('agente-all')")
    @PostMapping("/empresa/{empresaId}")
    public ResponseEntity<AgenteResponse> createAgente( 
    		@PathVariable Long empresaId,
    		@RequestBody SaveAgenteRequest request,
    		@RequestHeader("Authorization") String authorizationHeader ) {
    	
    	authorizationService.authorizeByEmpresa( empresaId, authorizationHeader );
    	
        AgenteResponse resp = agenteService.createAgente( request, empresaId );
        return ResponseEntity.ok( resp );
    }

    @UpdateAgenteDoc
    @PreAuthorize("hasAuthority('agente-all')")
    @PutMapping("/{agenteId}/empresa/{empresaId}")
    public ResponseEntity<String> updateAgente( 
    		@PathVariable Long agenteId, 
    		@PathVariable Long empresaId,
    		@RequestBody SaveAgenteRequest request,
    		@RequestHeader("Authorization") String authorizationHeader ) {
    	
    	authorizationService.authorizeByEmpresa( empresaId, authorizationHeader );
    	
        agenteService.updateAgente( agenteId, request, empresaId );
        return ResponseEntity.ok( "Agente alterado com sucesso." );
    }

    @FilterAgentesDoc
    @PreAuthorize("hasAuthority('agente-all')")
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<AgenteResponse>> filterAgentes( 
    		@PathVariable Long empresaId,
    		@RequestParam("nomepart") String nomePart,
    		@RequestHeader("Authorization") String authorizationHeader ) {

    	authorizationService.authorizeByEmpresa( empresaId, authorizationHeader );
    	
        List<AgenteResponse> responses = agenteService.filterAgentes( nomePart, empresaId );
        return ResponseEntity.ok( responses );
    }

    @GetAgenteDoc
    @PreAuthorize("hasAuthority('agente-all')")
    @GetMapping("/{agenteId}/get")
    public ResponseEntity<AgenteResponse> getAgente( 
    		@PathVariable Long agenteId,
    		@RequestHeader("Authorization") String authorizationHeader ) {
    	
    	authorizationService.authorizeAgenteOperByEmpresa( agenteId, authorizationHeader );
    	
        AgenteResponse resp = agenteService.getAgente( agenteId );
        return ResponseEntity.ok( resp );
    }

    @DeleteAgenteDoc
    @PreAuthorize("hasAuthority('agente-all')")
    @DeleteMapping("/{agenteId}")
    public ResponseEntity<String> deleteAgente( 
    		@PathVariable Long agenteId,
    		@RequestHeader("Authorization") String authorizationHeader ) {
    	
    	authorizationService.authorizeAgenteOperByEmpresa( agenteId, authorizationHeader );
    	
        agenteService.deleteAgente( agenteId );
        return ResponseEntity.ok( "Agente deletado com sucesso." );
    }

}

