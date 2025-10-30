package com.redemonitor.main.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.redemonitor.main.apidoc.login.LoginDoc;
import com.redemonitor.main.apidoc.login.LogoutDoc;
import com.redemonitor.main.apidoc.login.RefreshTokenDoc;
import com.redemonitor.main.dto.request.LoginRequest;
import com.redemonitor.main.dto.response.LoginResponse;
import com.redemonitor.main.service.LoginService;

@RestController
@RequestMapping("/main/api/v1/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @LoginDoc
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse httpResponse ) {
        LoginResponse loginResp = loginService.login( request, httpResponse );
        return ResponseEntity.ok( loginResp );
    }

    @LogoutDoc
    @PostMapping("/logout")
    public ResponseEntity<String> logout( HttpServletResponse httpResponse ) {
        loginService.logout( httpResponse );
        return ResponseEntity.ok( "Logout bem sucedido." );
    }

    @RefreshTokenDoc
    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(
            @CookieValue( "${jwt.refresh_token.cookie.name}") String refreshToken,
            HttpServletResponse httpResponse ) {
        LoginResponse resp = loginService.generateNewAccessToken( httpResponse, refreshToken );
        return ResponseEntity.ok( resp );
    }

}
