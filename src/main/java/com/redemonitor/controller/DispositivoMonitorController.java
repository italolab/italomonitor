package com.redemonitor.controller;

import com.redemonitor.apidoc.dispositivo.monitor.StartMonitoramentoDoc;
import com.redemonitor.apidoc.dispositivo.monitor.StopMonitoramentoDoc;
import com.redemonitor.service.DispositivoMonitorService;
import com.redemonitor.service.TokenService;
import com.redemonitor.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
