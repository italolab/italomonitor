package com.redemonitor.controller;

import com.redemonitor.apidoc.dispositivo.*;
import com.redemonitor.dto.request.SaveDispositivoRequest;
import com.redemonitor.dto.response.DispositivoResponse;
import com.redemonitor.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dispositivos")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;
    
    @CreateDispositivoDoc
    @PreAuthorize("hasAuthority('dispositivo-write')")
    @PostMapping
    public ResponseEntity<String> createDispositivo(@RequestBody SaveDispositivoRequest request ) {
        dispositivoService.createDispositivo( request );
        return ResponseEntity.ok( "Dispositivo registrado com sucesso." );
    }

    @UpdateDispositivoDoc
    @PreAuthorize("hasAuthority('dispositivo-write')")
    @PutMapping("/{dispositivoId}")
    public ResponseEntity<String> updateDispositivo( @PathVariable Long dispositivoId, @RequestBody SaveDispositivoRequest request ) {
        dispositivoService.updateDispositivo( dispositivoId, request );
        return ResponseEntity.ok( "Dispositivo alterado com sucesso." );
    }

    @FilterDispositivosDoc
    @PreAuthorize("hasAuthority('dispositivo-read')")
    @GetMapping
    public ResponseEntity<List<DispositivoResponse>> filterDispositivos(
            @RequestParam("hostpart") String hostPart,
            @RequestParam("nomepart") String nomePart,
            @RequestParam("localpart") String localPart ) {

        List<DispositivoResponse> responses =
                dispositivoService.filterDispositivos( hostPart, nomePart, localPart );

        return ResponseEntity.ok( responses );
    }

    @GetDispositivoDoc
    @PreAuthorize("hasAuthority('dispositivo-read')")
    @GetMapping("/{dispositivoId}/get")
    public ResponseEntity<DispositivoResponse> getDispositivo( @PathVariable Long dispositivoId ) {
        DispositivoResponse resp = dispositivoService.getDispositivo( dispositivoId );
        return ResponseEntity.ok( resp );
    }

    @DeleteDispositivoDoc
    @PreAuthorize("hasAuthority('dispositivo-delete')")
    @DeleteMapping("/{dispositivoId}")
    public ResponseEntity<String> deleteDispositivo( @PathVariable Long dispositivoId ) {
        dispositivoService.deleteDispositivo( dispositivoId );
        return ResponseEntity.ok( "Dispositivo deletado com sucesso." );
    }

}

