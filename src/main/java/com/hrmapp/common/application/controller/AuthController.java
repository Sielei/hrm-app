package com.hrmapp.common.application.controller;

import com.hrmapp.common.application.dto.GenericResponse;
import com.hrmapp.common.application.dto.request.LoginRequest;
import com.hrmapp.common.application.dto.request.PasswordResetRequest;
import com.hrmapp.common.application.dto.request.ResetPasswordRequest;
import com.hrmapp.common.application.port.input.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    @PostMapping("/get-password-reset-token")
    ResponseEntity<GenericResponse> getPasswordResetToken(@RequestBody PasswordResetRequest passwordResetRequest){
        authenticationService.generatePasswordResetToken(passwordResetRequest.emailAddress());
        return ResponseEntity.ok().body(GenericResponse.builder()
                        .success(true)
                        .message("A password reset link has been sent to " + passwordResetRequest.emailAddress())
                .build());
    }
    @PostMapping("/reset-password")
    public ResponseEntity<GenericResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        authenticationService.resetPasswordWithResetToken(resetPasswordRequest);
        return new ResponseEntity<>(GenericResponse.builder()
                .success(true)
                .message("Password reset successfully! Proceed to login page to login with your new password!")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<GenericResponse> logout(HttpServletRequest request){
        //TODO get user token and invalidate it
        //authenticationService.logout(request);
        return new ResponseEntity<>(GenericResponse.builder()
                .success(true)
                .message("Successfully logged out!")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> getRefreshToken(@RequestAttribute("userId") UUID userId, HttpServletRequest request){
        var refreshToken = authenticationService.getRefreshToken(userId, request);
        return new ResponseEntity<>(refreshToken, HttpStatus.OK);
    }
}
