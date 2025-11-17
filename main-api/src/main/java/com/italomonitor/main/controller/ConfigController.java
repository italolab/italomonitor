package com.italomonitor.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.italomonitor.main.apidoc.config.GetConfigDoc;
import com.italomonitor.main.apidoc.config.GetNoAdminConfigDoc;
import com.italomonitor.main.apidoc.config.UpdateConfigDoc;
import com.italomonitor.main.dto.request.SaveConfigRequest;
import com.italomonitor.main.dto.response.ConfigResponse;
import com.italomonitor.main.dto.response.NoAdminConfigResponse;
import com.italomonitor.main.service.ConfigService;

@RestController
@RequestMapping("/api/v1/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;
    
    @UpdateConfigDoc
    @PreAuthorize("hasAuthority('config-all')")
    @PostMapping
    public ResponseEntity<String> updateConfig( @RequestBody SaveConfigRequest request ) {
        configService.updateConfig( request );
        return ResponseEntity.ok( "Configuração salva com sucesso." );
    }

    @GetConfigDoc
    @PreAuthorize("hasAuthority('config-all')")
    @GetMapping("/load-monitor-server/{isLoadMonitorServer}/get")
    public ResponseEntity<ConfigResponse> getConfig( @PathVariable Boolean isLoadMonitorServer ) {    	
        ConfigResponse resp = configService.getConfig( isLoadMonitorServer );
        return ResponseEntity.ok( resp );
    }
    
    @GetNoAdminConfigDoc
    @PreAuthorize("hasAnyAuthority('config-all', 'no-admin-config-get')")
    @GetMapping("/get/no-admin")
    public ResponseEntity<NoAdminConfigResponse> getNoAdminConfig() {
    	NoAdminConfigResponse resp = configService.getNoAdminConfig();
    	return ResponseEntity.ok( resp );
    }
    
}
