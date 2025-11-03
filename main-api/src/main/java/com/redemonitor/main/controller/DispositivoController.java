package com.redemonitor.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redemonitor.main.apidoc.dispositivo.CreateDispositivoDoc;
import com.redemonitor.main.apidoc.dispositivo.DeleteDispositivoDoc;
import com.redemonitor.main.apidoc.dispositivo.FilterDispositivosDoc;
import com.redemonitor.main.apidoc.dispositivo.GetDispositivoDoc;
import com.redemonitor.main.apidoc.dispositivo.UpdateDispositivoDoc;
import com.redemonitor.main.apidoc.dispositivo.UpdateDispositivoStatusDoc;
import com.redemonitor.main.dto.request.SaveDispositivoRequest;
import com.redemonitor.main.dto.request.SaveDispositivoStatusRequest;
import com.redemonitor.main.dto.response.DispositivoResponse;
import com.redemonitor.main.service.DispositivoService;
import com.redemonitor.main.service.TokenService;

@RestController
@RequestMapping("/api/v1/dispositivos")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;
    
    @Autowired
    private TokenService tokenService;
            
    @CreateDispositivoDoc
    @PreAuthorize("hasAuthority('dispositivo-write')")
    @PostMapping
    public ResponseEntity<String> createDispositivo( @RequestBody SaveDispositivoRequest request ) {
        dispositivoService.createDispositivo( request );
        return ResponseEntity.ok( "Dispositivo registrado com sucesso." );
    }

    @UpdateDispositivoDoc
    @PreAuthorize("hasAuthority('dispositivo-write')")
    @PutMapping("/{dispositivoId}")
    public ResponseEntity<String> updateDispositivo( 
    		@PathVariable Long dispositivoId, 
    		@RequestBody SaveDispositivoRequest request ) {
    	
        dispositivoService.updateDispositivo( dispositivoId, request );
        return ResponseEntity.ok( "Dispositivo alterado com sucesso." );
    }
    
    @UpdateDispositivoStatusDoc
    @PreAuthorize("hasAnyAuthority('dispositivo-write', 'microservice')") 
    @PatchMapping("/{dispositivoId}/update-status")
    public ResponseEntity<String> updateDispositivoStatus( 
    		@PathVariable Long dispositivoId, 
    		@RequestBody SaveDispositivoStatusRequest request ) {
    	dispositivoService.updateStatus( dispositivoId, request );
    	return ResponseEntity.ok( "Status do dispositivo alterado com sucesso." );
    }

    @FilterDispositivosDoc
    @PreAuthorize("hasAuthority('dispositivo-read')")
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<DispositivoResponse>> filterDispositivos(
    		@PathVariable Long empresaId,
            @RequestParam("hostpart") String hostPart,
            @RequestParam("nomepart") String nomePart,
            @RequestParam("localpart") String localPart ) {

        List<DispositivoResponse> responses =
                dispositivoService.filterDispositivos( empresaId, hostPart, nomePart, localPart );

        return ResponseEntity.ok( responses );
    }

    @GetDispositivoDoc
    @PreAuthorize("hasAnyAuthority('dispositivo-read', 'microservice')")
    @GetMapping("/{dispositivoId}/get")
    public ResponseEntity<DispositivoResponse> getDispositivo( @PathVariable Long dispositivoId ) {
        DispositivoResponse resp = dispositivoService.getDispositivo( dispositivoId );
        return ResponseEntity.ok( resp );
    }

    @DeleteDispositivoDoc
    @PreAuthorize("hasAuthority('dispositivo-delete')")
    @DeleteMapping("/{dispositivoId}")
    public ResponseEntity<String> deleteDispositivo( 
    		@PathVariable Long dispositivoId, 
    		@RequestHeader("Authorization") String authorizationHeader ) {
    	    	    	
    	String username = tokenService.getUsernameByAuthorizationHeader( authorizationHeader );
    	
        dispositivoService.deleteDispositivo( dispositivoId, username );
        return ResponseEntity.ok( "Dispositivo deletado com sucesso." );
    }

}

