package com.redemonitor.disp_monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.StartMonitoramentoDoc;
import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.StopMonitoramentoDoc;
import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.UpdateConfigInMonitoresDoc;
import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.UpdateDispositivoInMonitorDoc;
import com.redemonitor.disp_monitor.service.DispositivoMonitorService;
import com.redemonitor.disp_monitor.util.BearerTokenUtil;

@RestController
@RequestMapping("/api/v1/monitoramento/dispositivos")
public class DispositivoMonitorController {

    @Autowired
    private DispositivoMonitorService dispositivoMonitorService;

    @Autowired
    private BearerTokenUtil bearerTokenUtil;

    @StartMonitoramentoDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento')")
    @PostMapping("/{dispositivoId}/start")
    public ResponseEntity<String> startMonitoramento(
            @PathVariable Long dispositivoId,
            @RequestHeader("Authorization") String authorizationHeader ) {

        String accessToken =  bearerTokenUtil.extractAccessToken( authorizationHeader );

        dispositivoMonitorService.startMonitoramento( dispositivoId, accessToken );
        return ResponseEntity.ok( "Monitoramento iniciado." );
    }

    @StopMonitoramentoDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento')")
    @PostMapping("/{dispositivoId}/stop")
    public ResponseEntity<String> stopMonitoramento(
            @PathVariable Long dispositivoId,
            @RequestHeader("Authorization") String authorizationHeader ) {

        String accessToken =  bearerTokenUtil.extractAccessToken( authorizationHeader );

        dispositivoMonitorService.stopMonitoramento( dispositivoId, accessToken );
        return ResponseEntity.ok( "Monitoramento parado." );
    }
    
    @UpdateConfigInMonitoresDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento')")
    @PostMapping("/update-config-in-monitores")
    public ResponseEntity<String> updateConfigInMonitores(
            @RequestHeader("Authorization") String authorizationHeader ) {
    	
        String accessToken =  bearerTokenUtil.extractAccessToken( authorizationHeader );

    	dispositivoMonitorService.updateConfigInMonitores( accessToken );
    	return ResponseEntity.ok( "Config atualizado nos monitores." );
    }
    
    @UpdateDispositivoInMonitorDoc
    @PreAuthorize("hasAuthority('dispositivo-monitoramento')")
    @PostMapping("/{dispositivoId}/update-dispositivo-in-monitor")
    public ResponseEntity<String> updateDispositivoInMonitor( 
    		@PathVariable Long dispositivoId, 
            @RequestHeader("Authorization") String authorizationHeader ) {
    	
        String accessToken =  bearerTokenUtil.extractAccessToken( authorizationHeader );

        dispositivoMonitorService.updateDispositivoInMonitor( dispositivoId, accessToken );
    	return ResponseEntity.ok( "Dispositivo atualizado no monitor dele." );
    } 

}
