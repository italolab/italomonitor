package com.redemonitor.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.redemonitor.main.apidoc.config.GetConfigDoc;
import com.redemonitor.main.apidoc.config.UpdateConfigDoc;
import com.redemonitor.main.dto.request.SaveConfigRequest;
import com.redemonitor.main.dto.response.ConfigResponse;
import com.redemonitor.main.service.ConfigService;

/*
 * A propriedade "jwt.access_token.cookie.name" está sendo acessada em algums métodos desse controller.
 */

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
    @GetMapping("/load-monitor-server/{isLoadMonitorServer}/get")
    public ResponseEntity<ConfigResponse> getConfig(
    		@PathVariable Boolean isLoadMonitorServer,
    		@CookieValue("${jwt.access_token.cookie.name}") String accessToken ) {
    	
        ConfigResponse resp = configService.getConfig( isLoadMonitorServer, accessToken );
        return ResponseEntity.ok( resp );
    }

}
