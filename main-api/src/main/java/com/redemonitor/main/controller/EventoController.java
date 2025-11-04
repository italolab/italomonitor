package com.redemonitor.main.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redemonitor.main.apidoc.evento.CreateEventoDoc;
import com.redemonitor.main.apidoc.evento.GetEventoDoc;
import com.redemonitor.main.apidoc.evento.ListEventosByDiaDoc;
import com.redemonitor.main.apidoc.evento.ListEventosByIntervaloDoc;
import com.redemonitor.main.dto.request.SaveEventoRequest;
import com.redemonitor.main.dto.response.EventoResponse;
import com.redemonitor.main.service.AuthorizationService;
import com.redemonitor.main.service.EventoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;
    
    @Autowired
    private AuthorizationService authorizationService;
    
    @CreateEventoDoc
    @PreAuthorize("hasAuthority('microservice')")  
    @PostMapping
    public ResponseEntity<String> createEvento( @RequestBody SaveEventoRequest request ) {
    	eventoService.createEvento( request );
    	return ResponseEntity.ok( "Evento criado com sucesso." );
    }

    @ListEventosByDiaDoc
    @PreAuthorize("hasAuthority('dispositivo-all')")
    @GetMapping("{dispositivoId}/dia/{dataDia}")
    public ResponseEntity<List<EventoResponse>> listByDia(
            @PathVariable Long dispositivoId,
            @PathVariable LocalDate dataDia,
            @RequestHeader("Authorization") String authorizationHeader ) {
    	
    	authorizationService.authorizeDispositivoOperByEmpresa( dispositivoId, authorizationHeader );
    	
        List<EventoResponse> responses = eventoService.listByDia( dispositivoId, dataDia );
        return ResponseEntity.ok( responses );
    }

    @ListEventosByIntervaloDoc
    @PreAuthorize("hasAuthority('dispositivo-all')")
    @GetMapping("/{dispositivoId}/diaIni/{dataDiaIni}/diaFim/{dataDiaFim}")
    public ResponseEntity<List<EventoResponse>> listByIntervalo(
            @PathVariable Long dispositivoId,
            @PathVariable LocalDate dataDiaIni,
            @PathVariable LocalDate dataDiaFim,
            @RequestHeader("Authorization") String authorizationHeader ) {
    	
    	authorizationService.authorizeDispositivoOperByEmpresa( dispositivoId, authorizationHeader );
    	
        List<EventoResponse> responses = eventoService.listByIntervalo( dispositivoId, dataDiaIni, dataDiaFim );
        return ResponseEntity.ok( responses );
    }
    
    @ListEventosByIntervaloDoc
    @PreAuthorize("hasAuthority('dispositivo-all')")
    @GetMapping("/{dispositivoId}/diaIni/{dataDiaIni}/diaFim/{dataDiaFim}/ordemInversa")
    public ResponseEntity<List<EventoResponse>> listByIntervaloOrdemInversa(
            @PathVariable Long dispositivoId,
            @PathVariable LocalDate dataDiaIni,
            @PathVariable LocalDate dataDiaFim,
            @RequestHeader("Authorization") String authorizationHeader ) {
    	
    	authorizationService.authorizeDispositivoOperByEmpresa( dispositivoId, authorizationHeader );
    	
        List<EventoResponse> responses = eventoService.listByIntervaloOrdemInversa( dispositivoId, dataDiaIni, dataDiaFim );
        return ResponseEntity.ok( responses );
    }

    @GetEventoDoc
    @PreAuthorize("hasAuthority('dispositivo-all')")
    @GetMapping("/get/{eventoId}")
    public ResponseEntity<EventoResponse> getEvento(
    		@PathVariable Long eventoId,
            @RequestHeader("Authorization") String authorizationHeader ) {
    	
    	authorizationService.authorizeEventoOperByEmpresa( eventoId, authorizationHeader );
    	
        EventoResponse resp = eventoService.getEvento( eventoId );
        return ResponseEntity.ok( resp );
    }

}
