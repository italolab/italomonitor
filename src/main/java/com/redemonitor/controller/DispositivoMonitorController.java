package com.redemonitor.controller;

import com.redemonitor.apidoc.dispositivo.monitor.StartMonitoramentoDoc;
import com.redemonitor.apidoc.dispositivo.monitor.StopMonitoramentoDoc;
import com.redemonitor.service.DispositivoMonitorService;
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
    private UsuarioService usuarioService;

    @StartMonitoramentoDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento')")
    @PostMapping("/start/{dispositivoId}")
    public ResponseEntity<String> startMonitoramento(
            @PathVariable Long dispositivoId, @CookieValue("${jwt.token.cookie.name}") String token ) {
        String username = usuarioService.getTokenUsername( token );

        dispositivoMonitorService.startMonitoramento( dispositivoId, username );
        return ResponseEntity.ok( "Monitoramento iniciado." );
    }

    @StopMonitoramentoDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento')")
    @PostMapping("/stop/{dispositivoId}")
    public ResponseEntity<String> stopMonitoramento(
            @PathVariable Long dispositivoId, @CookieValue( "${jwt.token.cookie.name}" ) String token ) {
        String username = usuarioService.getTokenUsername( token );

        dispositivoMonitorService.stopMonitoramento( dispositivoId, username );
        return ResponseEntity.ok( "Monitoramento parado." );
    }

}
