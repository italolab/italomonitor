package com.redemonitor.controller;

import com.redemonitor.apidoc.evento.GetEventoDoc;
import com.redemonitor.apidoc.evento.ListEventosByDiaDoc;
import com.redemonitor.apidoc.evento.ListEventosByIntervaloDoc;
import com.redemonitor.dto.response.EventoResponse;
import com.redemonitor.model.Evento;
import com.redemonitor.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @ListEventosByDiaDoc
    @PreAuthorize("hasAuthority('dispositivo-read')")
    @GetMapping("{dispositivoId}/dia/{dataDia}")
    public ResponseEntity<List<EventoResponse>> listByDia(
            @PathVariable Long dispositivoId,
            @PathVariable LocalDate dataDia ) {
        List<EventoResponse> responses = eventoService.listByDia( dispositivoId, dataDia );
        return ResponseEntity.ok( responses );
    }

    @ListEventosByIntervaloDoc
    @PreAuthorize("hasAuthority('dispositivo-read')")
    @GetMapping("/{dispositivoId}/diaIni/{dataDiaIni}/diaFim/{dataDiaFim}")
    public ResponseEntity<List<EventoResponse>> listByIntervalo(
            @PathVariable Long dispositivoId,
            @PathVariable LocalDate dataDiaIni,
            @PathVariable LocalDate dataDiaFim ) {
        List<EventoResponse> responses = eventoService.listByIntervalo( dispositivoId, dataDiaIni, dataDiaFim );
        return ResponseEntity.ok( responses );
    }

    @GetEventoDoc
    @PreAuthorize("hasAuthority('dispositivo-read')")
    @GetMapping("/get/{eventoId}")
    public ResponseEntity<EventoResponse> getEvento( @PathVariable Long eventoId ) {
        EventoResponse resp = eventoService.getEvento( eventoId );
        return ResponseEntity.ok( resp );
    }

}
