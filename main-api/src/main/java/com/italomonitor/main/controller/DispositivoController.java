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
import org.springframework.web.bind.annotation.RestController;

import com.italomonitor.main.apidoc.dispositivo.CreateDispositivoDoc;
import com.italomonitor.main.apidoc.dispositivo.DeleteDispositivoDoc;
import com.italomonitor.main.apidoc.dispositivo.GetDispositivoDoc;
import com.italomonitor.main.apidoc.dispositivo.GetDispositivosInfosDoc;
import com.italomonitor.main.apidoc.dispositivo.ListDispositivosDoc;
import com.italomonitor.main.apidoc.dispositivo.UpdateDispositivoDoc;
import com.italomonitor.main.dto.request.SaveDispositivoRequest;
import com.italomonitor.main.dto.response.DispositivoResponse;
import com.italomonitor.main.dto.response.DispositivosInfosResponse;
import com.italomonitor.main.service.AuthorizationService;
import com.italomonitor.main.service.DispositivoService;

@RestController
@RequestMapping("/api/v1/dispositivos")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;
        
    @Autowired
    private AuthorizationService authorizationService;
            
    @GetDispositivosInfosDoc
    @PreAuthorize("hasAuthority('dispositivo-all')")
    @GetMapping("/empresa/{empresaId}/infos")
    public ResponseEntity<DispositivosInfosResponse> getDispositivosInfo( 
    		@PathVariable Long empresaId, 
    		@RequestHeader( "Authorization" ) String authorizationHeader ) {
    	
    	authorizationService.authorizeByEmpresa( empresaId, authorizationHeader );
    	
    	DispositivosInfosResponse resp = dispositivoService.getDispositivosInfos( empresaId );
    	return ResponseEntity.ok( resp );
    }
    
    @CreateDispositivoDoc
    @PreAuthorize("hasAuthority('dispositivo-all')")
    @PostMapping
    public ResponseEntity<String> createDispositivo( 
    		@RequestBody SaveDispositivoRequest request, 
    		@RequestHeader("Authorization") String authorizationHeader ) {
    	
    	Long empresaId = request.getEmpresaId();
    	authorizationService.authorizeByEmpresa( empresaId, authorizationHeader );
    	
        dispositivoService.createDispositivo( request );
        return ResponseEntity.ok( "Dispositivo registrado com sucesso." );
    }

    @UpdateDispositivoDoc
    @PreAuthorize("hasAuthority('dispositivo-all')")
    @PutMapping("/{dispositivoId}")
    public ResponseEntity<String> updateDispositivo( 
    		@PathVariable Long dispositivoId, 
    		@RequestBody SaveDispositivoRequest request, 
    		@RequestHeader("Authorization") String authorizationHeader ) {
    	
    	Long empresaId = request.getEmpresaId();
    	authorizationService.authorizeByEmpresa( empresaId, authorizationHeader );
    	
        dispositivoService.updateDispositivo( dispositivoId, request );
        return ResponseEntity.ok( "Dispositivo alterado com sucesso." );
    }
    
    @ListDispositivosDoc
    @PreAuthorize("hasAuthority('dispositivo-all')")
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<DispositivoResponse>> filterDispositivos(
    		@PathVariable Long empresaId,
    		@RequestHeader("Authorization") String authorizationHeader ) {
    	
    	authorizationService.authorizeByEmpresa( empresaId, authorizationHeader );

        List<DispositivoResponse> responses =
                dispositivoService.listDispositivos( empresaId );

        return ResponseEntity.ok( responses );
    }
    
    @GetDispositivoDoc
    @PreAuthorize("hasAnyAuthority('dispositivo-all')")
    @GetMapping("/{dispositivoId}/get/no-admin")
    public ResponseEntity<DispositivoResponse> getDispositivo( 
    		@PathVariable Long dispositivoId,
    		@RequestHeader("Authorization") String authorizationHeader ) {
    	
    	authorizationService.authorizeDispositivoOperByEmpresa( dispositivoId, authorizationHeader );
    	
        DispositivoResponse resp = dispositivoService.getDispositivo( dispositivoId );
        return ResponseEntity.ok( resp );
    }    

    @DeleteDispositivoDoc
    @PreAuthorize("hasAuthority('dispositivo-all')")
    @DeleteMapping("/{dispositivoId}")
    public ResponseEntity<String> deleteDispositivo( 
    		@PathVariable Long dispositivoId,
    		@RequestHeader("Authorization") String authorizationHeader ) {
    	    	    	
    	authorizationService.authorizeDispositivoOperByEmpresa( dispositivoId, authorizationHeader );
    	    	
    	dispositivoService.deleteDispositivo( dispositivoId );
        return ResponseEntity.ok( "Dispositivo deletado com sucesso." );
    }

}

