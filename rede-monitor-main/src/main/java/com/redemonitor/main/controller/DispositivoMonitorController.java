package com.redemonitor.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redemonitor.main.apidoc.dispositivo.monitor.StartMonitoramentoDoc;
import com.redemonitor.main.apidoc.dispositivo.monitor.StopMonitoramentoDoc;
import com.redemonitor.main.integration.DispositivoMonitorIntegration;

/*
 * A propriedade "jwt.access_token.cookie.name" está sendo acessada em algums métodos desse controller.
 */

@RestController
@RequestMapping("/api/v1/dispositivos/monitoramento")
public class DispositivoMonitorController {

    @Autowired
    private DispositivoMonitorIntegration dispositivoMonitorIntegration;

    @StartMonitoramentoDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento')")
    @PostMapping("/start/{dispositivoId}")
    public ResponseEntity<String> startMonitoramento(
            @PathVariable Long dispositivoId,
            @CookieValue("${jwt.access_token.cookie.name}") String accessToken ) {

        dispositivoMonitorIntegration.startMonitoramento( dispositivoId, accessToken );
        return ResponseEntity.ok( "Monitoramento iniciado." );
    }

    @StopMonitoramentoDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento')")
    @PostMapping("/stop/{dispositivoId}")
    public ResponseEntity<String> stopMonitoramento(
            @PathVariable Long dispositivoId,
            @CookieValue( "${jwt.access_token.cookie.name}" ) String accessToken ) {

        dispositivoMonitorIntegration.stopMonitoramento( dispositivoId, accessToken );
        return ResponseEntity.ok( "Monitoramento parado." );
    }

}
