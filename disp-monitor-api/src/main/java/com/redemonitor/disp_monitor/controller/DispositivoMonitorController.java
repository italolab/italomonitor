package com.redemonitor.disp_monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.GetExisteNoMonitorDoc;
import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.GetInfoDoc;
import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.StartMonitoramentoDoc;
import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.StopMonitoramentoDoc;
import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.UpdateConfigInMonitoresDoc;
import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.UpdateDispositivoInMonitorDoc;
import com.redemonitor.disp_monitor.dto.ExisteNoMonitorResponse;
import com.redemonitor.disp_monitor.dto.InfoResponse;
import com.redemonitor.disp_monitor.dto.MonitoramentoOperResponse;
import com.redemonitor.disp_monitor.service.DispositivoMonitorService;

@RestController
@RequestMapping("/api/v1/monitoramento/dispositivos")
public class DispositivoMonitorController {

    @Autowired
    private DispositivoMonitorService dispositivoMonitorService;

    @StartMonitoramentoDoc
    @PreAuthorize("hasAuthority('microservice')")
    @PostMapping("/{dispositivoId}/username/{username}/start")
    public ResponseEntity<MonitoramentoOperResponse> startMonitoramento(
            @PathVariable Long dispositivoId,
            @PathVariable String username ) {

        MonitoramentoOperResponse resp = dispositivoMonitorService.startMonitoramento( dispositivoId, username );
        return ResponseEntity.ok( resp );
    }

    @StopMonitoramentoDoc
    @PreAuthorize("hasAuthority('microservice')")
    @PostMapping("/{dispositivoId}/stop")
    public ResponseEntity<MonitoramentoOperResponse> stopMonitoramento( @PathVariable Long dispositivoId ) {
        MonitoramentoOperResponse resp = dispositivoMonitorService.stopMonitoramento( dispositivoId );
        return ResponseEntity.ok( resp );
    }
    
    @UpdateConfigInMonitoresDoc
    @PreAuthorize("hasAuthority('microservice')")
    @PostMapping("/update-config-in-monitores")
    public ResponseEntity<MonitoramentoOperResponse> updateConfigInMonitores() {    	
    	MonitoramentoOperResponse resp = dispositivoMonitorService.updateConfigInMonitores();
    	return ResponseEntity.ok( resp );
    }
    
    @UpdateDispositivoInMonitorDoc
    @PreAuthorize("hasAuthority('microservice')")
    @PostMapping("/{dispositivoId}/update-dispositivo-in-monitor")
    public ResponseEntity<MonitoramentoOperResponse> updateDispositivoInMonitor( @PathVariable Long dispositivoId ) {    	
        MonitoramentoOperResponse resp = dispositivoMonitorService.updateDispositivoInMonitor( dispositivoId );
    	return ResponseEntity.ok( resp );
    } 

    @GetInfoDoc
    @PreAuthorize("hasAuthority('microservice')")
    @GetMapping("/info")
    public ResponseEntity<InfoResponse> getInfo() {
    	InfoResponse resp = dispositivoMonitorService.getInfo();
    	return ResponseEntity.ok( resp );
    }
    
    @GetExisteNoMonitorDoc
    @PreAuthorize("hasAuthority('microservice')")
    @GetMapping("/{dispositivoId}/existe-no-monitor")
    public ResponseEntity<ExisteNoMonitorResponse> getExisteNoMonitor( @PathVariable Long dispositivoId ) {
    	ExisteNoMonitorResponse resp = dispositivoMonitorService.existeNoMonitor( dispositivoId );
    	return ResponseEntity.ok( resp );
    }
    
}
