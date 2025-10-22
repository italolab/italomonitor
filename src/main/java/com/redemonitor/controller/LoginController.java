package com.redemonitor.controller;

import com.redemonitor.apidoc.login.LoginDoc;
import com.redemonitor.dto.request.LoginRequest;
import com.redemonitor.dto.response.LoginResponse;
import com.redemonitor.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @LoginDoc
    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse httpResponse ) {
        LoginResponse loginResp = loginService.login( request, httpResponse );
        return ResponseEntity.ok( loginResp );
    }

}
