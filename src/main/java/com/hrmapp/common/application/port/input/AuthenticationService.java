package com.hrmapp.common.application.port.input;

import com.hrmapp.common.application.dto.GenericResponse;
import com.hrmapp.common.application.dto.LoginResponse;
import com.hrmapp.common.application.dto.request.LoginRequest;
import com.hrmapp.common.infrastructure.util.JwtUtil;
import com.hrmapp.user.application.port.input.UserApplicationService;
import com.hrmapp.user.domain.entity.Session;
import com.hrmapp.user.domain.valueobject.IPAddress;
import com.hrmapp.user.domain.valueobject.UserStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.slf4j.SLF4JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthenticationService {
    private final UserApplicationService userApplicationService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private static final Logger log = LoggerFactory.getLogger(SLF4JLogger.class);

    public AuthenticationService(UserApplicationService userApplicationService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userApplicationService = userApplicationService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> login(LoginRequest loginRequest, HttpServletRequest request) {
        var requestIp = request.getRemoteAddr();
        log.info("Login attempted by user: {} from IP address: {}", loginRequest.username(), requestIp);
        var userDto = userApplicationService.findUserByUsername(loginRequest.username());
        if (userDto.userStatus() != UserStatus.ACTIVE){
            return new ResponseEntity<>(GenericResponse.builder()
                    .success(false)
                    .message("User is deactivated!")
                    .build(), HttpStatus.FORBIDDEN);
        }
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
        var isAuthenticated = false;
        try {
            var authManager = authenticationManager.authenticate(authenticationToken);
            isAuthenticated = authManager.isAuthenticated();
            log.info("{} authenticated successfully", userDto.userStatus());
        }
        catch (AuthenticationException authenticationException){
            log.error("Authentication for {} failed because of {}!", userDto.username(), authenticationException.getMessage());
            log.error("Authentication exception: ", authenticationException);
        }
        if (isAuthenticated){
            var jwtToken = jwtUtil.generateJWTToken(userDto);
            var session = Session.builder()
                    .token(jwtToken)
                    .userId(userDto.id())
                    .startedAt(Instant.now())
                    .isActive(true)
                    .expectedTerminationTime(jwtUtil.getTokenExpiry(jwtToken))
                    .ipAddress(new IPAddress(requestIp))
                    .build();
            userApplicationService.createSession(session);
            return new ResponseEntity<>(LoginResponse.builder()
                    .token(jwtToken)
                    .build(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(GenericResponse.builder()
                    .success(false)
                    .message("Failed to authenticate user! Username or password is incorrect!")
                    .build(), HttpStatus.UNAUTHORIZED);
        }
    }
}
