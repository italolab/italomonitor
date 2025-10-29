package com.redemonitor.controller;

import com.redemonitor.apidoc.config.GetConfigDoc;
import com.redemonitor.apidoc.config.UpdateConfigDoc;
import com.redemonitor.dto.request.SaveConfigRequest;
import com.redemonitor.dto.response.ConfigResponse;
import com.redemonitor.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @UpdateConfigDoc
    @PreAuthorize("hasAuthority('config-write')")
    @PostMapping
    public ResponseEntity<String> updateConfig( @RequestBody SaveConfigRequest request ) {
        configService.updateConfig( request );
        return ResponseEntity.ok( "Configuração salva com sucesso." );
    }

    @GetConfigDoc
    @PreAuthorize("hasAuthority('config-read')")
    @GetMapping
    public ResponseEntity<ConfigResponse> getConfig() {
        ConfigResponse resp = configService.getConfig();
        return ResponseEntity.ok( resp );
    }

}
