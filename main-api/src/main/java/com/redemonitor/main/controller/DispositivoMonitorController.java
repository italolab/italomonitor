package com.redemonitor.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redemonitor.main.apidoc.dispositivo.monitor.StartAllMonitoramentosDoc;
import com.redemonitor.main.apidoc.dispositivo.monitor.StartMonitoramentoDoc;
import com.redemonitor.main.apidoc.dispositivo.monitor.StopAllMonitoramentosDoc;
import com.redemonitor.main.apidoc.dispositivo.monitor.StopMonitoramentoDoc;
import com.redemonitor.main.components.DispositivoMonitorEscalonador;
import com.redemonitor.main.components.util.JwtTokenUtil;

@RestController
@RequestMapping("/api/v1/monitoramento/dispositivos")
public class DispositivoMonitorController {

    @Autowired
    private DispositivoMonitorEscalonador dispositivoMonitorEscalonador;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @StartAllMonitoramentosDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento-all')")
    @PostMapping("/empresa/{empresaId}/start-all")
    public ResponseEntity<String> startAllMonitoramentos(
            @PathVariable Long empresaId,
            @RequestHeader("Authorization") String authorizationHeader ) {

    	String username = jwtTokenUtil.extractInfos( authorizationHeader ).getUsername();

        dispositivoMonitorEscalonador.startAllMonitoramentos( empresaId, username );
        return ResponseEntity.ok( "Todos os monitoramento iniciados." );
    }
    	
    @StopAllMonitoramentosDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento-all')")
    @PostMapping("/empresa/{empresaId}/stop-all")
    public ResponseEntity<String> stopAllMonitoramentos(
            @PathVariable Long empresaId,
            @RequestHeader("Authorization") String authorizationHeader ) {

    	String username = jwtTokenUtil.extractInfos( authorizationHeader ).getUsername();

    	dispositivoMonitorEscalonador.stopAllMonitoramentos( empresaId, username );
        return ResponseEntity.ok( "Todos os monitoramento finalizados." );
    }
    
    @StartMonitoramentoDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento-all')")
    @PostMapping("/{dispositivoId}/start")
    public ResponseEntity<String> startMonitoramento(
            @PathVariable Long dispositivoId,
            @RequestHeader("Authorization") String authorizationHeader ) {

    	String username = jwtTokenUtil.extractInfos( authorizationHeader ).getUsername();

        dispositivoMonitorEscalonador.startMonitoramento( dispositivoId, username );
        return ResponseEntity.ok( "Monitoramento iniciado." );
    }

    @StopMonitoramentoDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento-all')")
    @PostMapping("/{dispositivoId}/stop")
    public ResponseEntity<String> stopMonitoramento(
            @PathVariable Long dispositivoId,
            @RequestHeader("Authorization") String authorizationHeader ) {

    	String username = jwtTokenUtil.extractInfos( authorizationHeader ).getUsername();

        dispositivoMonitorEscalonador.stopMonitoramento( dispositivoId, username );
        return ResponseEntity.ok( "Monitoramento parado." );
    }

}
