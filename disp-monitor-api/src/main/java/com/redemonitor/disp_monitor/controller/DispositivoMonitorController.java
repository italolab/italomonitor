package com.redemonitor.disp_monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.GetExisteNoMonitorDoc;
import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.GetInfoDoc;
import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.StartMonitoramentoDoc;
import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.StopAllMonitoramentosDoc;
import com.redemonitor.disp_monitor.apidoc.dispositivo.monitor.StopMonitoramentoDoc;
import com.redemonitor.disp_monitor.dto.request.StartMonitoramentoRequest;
import com.redemonitor.disp_monitor.dto.response.ExisteNoMonitorResponse;
import com.redemonitor.disp_monitor.dto.response.InfoResponse;
import com.redemonitor.disp_monitor.dto.response.MonitoramentoOperResponse;
import com.redemonitor.disp_monitor.service.DispositivoMonitorService;
import com.redemonitor.disp_monitor.service.SistemaService;

@RestController
@RequestMapping("/api/v1/monitoramento/dispositivos")
public class DispositivoMonitorController {

    @Autowired
    private DispositivoMonitorService dispositivoMonitorService;

    @Autowired
    private SistemaService sistemaService;
    
    @StartMonitoramentoDoc
    @PreAuthorize("hasAuthority('microservice')")
    @PostMapping("/start")
    public ResponseEntity<MonitoramentoOperResponse> startMonitoramento(
            @RequestBody StartMonitoramentoRequest request ) {

        MonitoramentoOperResponse resp = dispositivoMonitorService.startMonitoramento( request );
        return ResponseEntity.ok( resp );
    }

    @StopMonitoramentoDoc
    @PreAuthorize("hasAuthority('microservice')")
    @PostMapping("/{dispositivoId}/stop")
    public ResponseEntity<MonitoramentoOperResponse> stopMonitoramento( @PathVariable Long dispositivoId ) {
        MonitoramentoOperResponse resp = dispositivoMonitorService.stopMonitoramento( dispositivoId );
        return ResponseEntity.ok( resp );
    }
    
    @StopAllMonitoramentosDoc
    @PreAuthorize("hasAuthority('microservice')")
    @PostMapping("/stop-all")
    public ResponseEntity<String> stopAllMonitoramentos() {
    	dispositivoMonitorService.stopAllMonitoramentos();
    	return ResponseEntity.ok( "Monitoramentos finalizados com sucesso." );
    }
    
    @GetInfoDoc
    @PreAuthorize("hasAuthority('microservice')")
    @GetMapping("/info")
    public ResponseEntity<InfoResponse> getInfo() {
    	InfoResponse resp = sistemaService.getInfo();
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
