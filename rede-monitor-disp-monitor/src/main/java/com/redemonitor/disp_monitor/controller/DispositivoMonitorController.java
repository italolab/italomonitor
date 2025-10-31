package com.redemonitor.disp_monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.StartMonitoramentoDoc;
import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.StopMonitoramentoDoc;
import com.redemonitor.disp_monitor.service.DispositivoMonitorService;
import com.redemonitor.disp_monitor.service.TokenService;

/*
 * A propriedade "jwt.access_token.cookie.name" está sendo acessada em algums métodos desse controller.
 */

@RestController
@RequestMapping("/api/v1/dispositivos/monitoramento")
public class DispositivoMonitorController {

    @Autowired
    private DispositivoMonitorService dispositivoMonitorService;

    @Autowired
    private TokenService tokenService;

    @StartMonitoramentoDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento')")
    @PostMapping("/start/{dispositivoId}")
    public ResponseEntity<String> startMonitoramento(
            @PathVariable Long dispositivoId,
            @CookieValue("${jwt.access_token.cookie.name}") String accessToken ) {

        String username =  tokenService.getUsername( accessToken );

        dispositivoMonitorService.startMonitoramento( dispositivoId, username );
        return ResponseEntity.ok( "Monitoramento iniciado." );
    }

    @StopMonitoramentoDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento')")
    @PostMapping("/stop/{dispositivoId}")
    public ResponseEntity<String> stopMonitoramento(
            @PathVariable Long dispositivoId,
            @CookieValue( "${jwt.access_token.cookie.name}" ) String accessToken ) {

        String username = tokenService.getUsername( accessToken );

        dispositivoMonitorService.stopMonitoramento( dispositivoId, username );
        return ResponseEntity.ok( "Monitoramento parado." );
    }

}
