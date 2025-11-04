package com.redemonitor.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redemonitor.main.apidoc.monitor_server.CreateMonitorServerDoc;
import com.redemonitor.main.apidoc.monitor_server.DeleteMonitorServerDoc;
import com.redemonitor.main.apidoc.monitor_server.FilterMonitorServersDoc;
import com.redemonitor.main.apidoc.monitor_server.GetMonitorServerDoc;
import com.redemonitor.main.apidoc.monitor_server.UpdateMonitorServerDoc;
import com.redemonitor.main.dto.request.SaveMonitorServerRequest;
import com.redemonitor.main.dto.response.MonitorServerResponse;
import com.redemonitor.main.service.MonitorServerService;

@RestController
@RequestMapping("/api/v1/monitor-servers")
public class MonitorServerController {

    @Autowired
    private MonitorServerService monitorServerService;
        
    @CreateMonitorServerDoc
    @PreAuthorize("hasAuthority('config-all')")
    @PostMapping
    public ResponseEntity<String> createMonitorServer(@RequestBody SaveMonitorServerRequest request ) {
        monitorServerService.createMonitorServer( request );
        return ResponseEntity.ok( "Servidor de monitoramento registrado com sucesso." );
    }

    @UpdateMonitorServerDoc
    @PreAuthorize("hasAuthority('config-all')")
    @PutMapping("/{monitorServerId}")
    public ResponseEntity<String> updateMonitorServer( @PathVariable Long monitorServerId, @RequestBody SaveMonitorServerRequest request ) {
        monitorServerService.updateMonitorServer( monitorServerId, request );
        return ResponseEntity.ok( "Servidor de monitoramento alterado com sucesso." );
    }

    @FilterMonitorServersDoc
    @PreAuthorize("hasAuthority('config-all')")
    @GetMapping
    public ResponseEntity<List<MonitorServerResponse>> filterMonitorServers( @RequestParam("hostpart") String hostPart ) {    	
        List<MonitorServerResponse> responses = monitorServerService.filterMonitorServers( hostPart );
        return ResponseEntity.ok( responses );
    }

    @GetMonitorServerDoc
    @PreAuthorize("hasAuthority('config-all')")
    @GetMapping("/{monitorServerId}/get")
    public ResponseEntity<MonitorServerResponse> getMonitorServer( @PathVariable Long monitorServerId ) {    	
        MonitorServerResponse resp = monitorServerService.getMonitorServer( monitorServerId );
        return ResponseEntity.ok( resp );
    }

    @DeleteMonitorServerDoc
    @PreAuthorize("hasAuthority('config-all')")
    @DeleteMapping("/{monitorServerId}")
    public ResponseEntity<String> deleteMonitorServer( @PathVariable Long monitorServerId ) {
        monitorServerService.deleteMonitorServer( monitorServerId );
        return ResponseEntity.ok( "Servidor de monitoramento deletado com sucesso." );
    }

}

