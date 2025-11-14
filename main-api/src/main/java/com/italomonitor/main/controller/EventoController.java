package com.italomonitor.main.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.italomonitor.main.apidoc.evento.GetEventoDoc;
import com.italomonitor.main.apidoc.evento.ListEventosByDiaDoc;
import com.italomonitor.main.apidoc.evento.ListEventosByIntervaloDoc;
import com.italomonitor.main.dto.response.EventoResponse;
import com.italomonitor.main.service.AuthorizationService;
import com.italomonitor.main.service.EventoService;

@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;
    
    @Autowired
    private AuthorizationService authorizationService;
    
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
    @GetMapping("/{dispositivoId}/diaIni/{dataDiaIni}/diaFim/{dataDiaFim}/ascendente/{ascendente}")
    public ResponseEntity<List<EventoResponse>> listByIntervalo(
            @PathVariable Long dispositivoId,
            @PathVariable LocalDate dataDiaIni,
            @PathVariable LocalDate dataDiaFim,
            @PathVariable boolean ascendente,
            @RequestHeader("Authorization") String authorizationHeader ) {
    	
    	authorizationService.authorizeDispositivoOperByEmpresa( dispositivoId, authorizationHeader );
    	
        List<EventoResponse> responses = eventoService.listByIntervalo( dispositivoId, dataDiaIni, dataDiaFim, ascendente );
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
