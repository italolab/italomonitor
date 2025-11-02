package com.redemonitor.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redemonitor.main.apidoc.dispositivo.monitor.StartAllMonitoramentosDoc;
import com.redemonitor.main.apidoc.dispositivo.monitor.StartMonitoramentoDoc;
import com.redemonitor.main.apidoc.dispositivo.monitor.StopAllMonitoramentosDoc;
import com.redemonitor.main.apidoc.dispositivo.monitor.StopMonitoramentoDoc;
import com.redemonitor.main.components.DispositivoMonitorEscalonador;

/*
 * A propriedade "jwt.access_token.cookie.name" está sendo acessada em algums métodos desse controller.
 */

@RestController
@RequestMapping("/api/v1/monitoramento/dispositivos")
public class DispositivoMonitorController {

    @Autowired
    private DispositivoMonitorEscalonador dispositivoMonitorEscalonador;

    @StartAllMonitoramentosDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento')")
    @PostMapping("/empresa/{empresaId}/start-all")
    public ResponseEntity<String> startAllMonitoramentos(
            @PathVariable Long empresaId,
            @CookieValue("${jwt.access_token.cookie.name}") String accessToken ) {

        dispositivoMonitorEscalonador.startAllMonitoramentos( empresaId, accessToken );
        return ResponseEntity.ok( "Todos os monitoramento iniciados." );
    }
    	
    @StopAllMonitoramentosDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento')")
    @PostMapping("/empresa/{empresaId}/stop-all")
    public ResponseEntity<String> stopAllMonitoramentos(
            @PathVariable Long empresaId,
            @CookieValue("${jwt.access_token.cookie.name}") String accessToken ) {

        dispositivoMonitorEscalonador.stopAllMonitoramentos( empresaId, accessToken );
        return ResponseEntity.ok( "Todos os monitoramento finalizados." );
    }
    
    @StartMonitoramentoDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento')")
    @PostMapping("/{dispositivoId}/start")
    public ResponseEntity<String> startMonitoramento(
            @PathVariable Long dispositivoId,
            @CookieValue("${jwt.access_token.cookie.name}") String accessToken ) {

        dispositivoMonitorEscalonador.startMonitoramento( dispositivoId, accessToken );
        return ResponseEntity.ok( "Monitoramento iniciado." );
    }

    @StopMonitoramentoDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento')")
    @PostMapping("/{dispositivoId}/stop")
    public ResponseEntity<String> stopMonitoramento(
            @PathVariable Long dispositivoId,
            @CookieValue( "${jwt.access_token.cookie.name}" ) String accessToken ) {

        dispositivoMonitorEscalonador.stopMonitoramento( dispositivoId, accessToken );
        return ResponseEntity.ok( "Monitoramento parado." );
    }

}
