package com.hrmapp.common.application.controller;

import com.hrmapp.common.application.dto.request.LoginRequest;
import com.hrmapp.common.application.port.input.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody @Validated LoginRequest loginRequest, HttpServletRequest httpServletRequest){
        return authenticationService.login(loginRequest, httpServletRequest);
    }
}
