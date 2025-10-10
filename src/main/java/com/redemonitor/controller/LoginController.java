package com.redemonitor.controller;

import com.redemonitor.controller.dto.request.LoginRequest;
import com.redemonitor.controller.dto.response.LoginResponse;
import com.redemonitor.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> login( @RequestBody LoginRequest request ) {
        LoginResponse loginResp = loginService.login( request );
        return ResponseEntity.ok( loginResp );
    }

}
