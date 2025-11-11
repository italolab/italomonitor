package com.redemonitor.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redemonitor.main.apidoc.dispositivo.monitor.StartEmpresaMonitoramentosDoc;
import com.redemonitor.main.apidoc.dispositivo.monitor.StartMonitoramentoDoc;
import com.redemonitor.main.apidoc.dispositivo.monitor.StopAllMonitoramentosDoc;
import com.redemonitor.main.apidoc.dispositivo.monitor.StartAllMonitoramentosDoc;
import com.redemonitor.main.apidoc.dispositivo.monitor.StopEmpresaMonitoramentosDoc;
import com.redemonitor.main.apidoc.dispositivo.monitor.StopMonitoramentoDoc;
import com.redemonitor.main.components.DispositivoMonitorEscalonador;

@RestController
@RequestMapping("/api/v1/monitoramento/dispositivos")
public class DispositivoMonitorController {

    @Autowired
    private DispositivoMonitorEscalonador dispositivoMonitorEscalonador;
    
    @StartAllMonitoramentosDoc
    @PreAuthorize("hasAuthority('start-or-stop-all-monitoramentos')") 
    @PostMapping("/start-all-monitoramentos")
    public ResponseEntity<String> startAllMonitoramentos() {
    	String resp = dispositivoMonitorEscalonador.startAllMonitoramentos();
    	return ResponseEntity.ok( resp ); 
    }
    
    @StopAllMonitoramentosDoc
    @PreAuthorize("hasAuthority('start-or-stop-all-monitoramentos')") 
    @PostMapping("/stop-all-monitoramentos")
    public ResponseEntity<String> stopAllMonitoramentos() {
    	String resp = dispositivoMonitorEscalonador.stopAllMonitoramentos();
    	return ResponseEntity.ok( resp ); 
    }
    
    @StartEmpresaMonitoramentosDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento-all')")
    @PostMapping("/empresa/{empresaId}/start-all")
    public ResponseEntity<String> startEmpresaMonitoramentos(
            @PathVariable Long empresaId,
            @RequestHeader("Authorization") String authorizationHeader ) {

        dispositivoMonitorEscalonador.startEmpresaMonitoramentos( empresaId );
        return ResponseEntity.ok( "Todos os monitoramento iniciados." );
    }
    	
    @StopEmpresaMonitoramentosDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento-all')")
    @PostMapping("/empresa/{empresaId}/stop-all")
    public ResponseEntity<String> stopEmpresaMonitoramentos(
            @PathVariable Long empresaId,
            @RequestHeader("Authorization") String authorizationHeader ) {

    	dispositivoMonitorEscalonador.stopEmpresaMonitoramentos( empresaId );
        return ResponseEntity.ok( "Todos os monitoramento finalizados." );
    }
    
    @StartMonitoramentoDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento-all')")
    @PostMapping("/{dispositivoId}/start")
    public ResponseEntity<String> startMonitoramento(
            @PathVariable Long dispositivoId,
            @RequestHeader("Authorization") String authorizationHeader ) {

        dispositivoMonitorEscalonador.startMonitoramento( dispositivoId );
        return ResponseEntity.ok( "Monitoramento iniciado." );
    }

    @StopMonitoramentoDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento-all')")
    @PostMapping("/{dispositivoId}/stop")
    public ResponseEntity<String> stopMonitoramento(
            @PathVariable Long dispositivoId,
            @RequestHeader("Authorization") String authorizationHeader ) {

        dispositivoMonitorEscalonador.stopMonitoramento( dispositivoId );
        return ResponseEntity.ok( "Monitoramento parado." );
    }

}
