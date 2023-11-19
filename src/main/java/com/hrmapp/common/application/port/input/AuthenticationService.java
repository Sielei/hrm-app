package com.hrmapp.common.application.port.input;

import com.hrmapp.common.application.dto.GenericResponse;
import com.hrmapp.common.application.dto.LoginResponse;
import com.hrmapp.common.application.dto.request.LoginRequest;
import com.hrmapp.common.application.dto.request.ResetPasswordRequest;
import com.hrmapp.common.application.port.input.util.PasswordUtil;
import com.hrmapp.common.infrastructure.util.JwtUtil;
import com.hrmapp.email.util.EmailUtil;
import com.hrmapp.user.application.dto.command.CreatePasswordResetTokenCommand;
import com.hrmapp.user.application.dto.command.UpdatePasswordCommand;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class AuthenticationService {
    private final UserApplicationService userApplicationService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailUtil emailUtil;
    private final PasswordUtil passwordUtil;
    private static final Logger log = LoggerFactory.getLogger(SLF4JLogger.class);

    public AuthenticationService(UserApplicationService userApplicationService, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil, EmailUtil emailUtil, PasswordUtil passwordUtil) {
        this.userApplicationService = userApplicationService;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.emailUtil = emailUtil;
        this.passwordUtil = passwordUtil;
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
    public void  generatePasswordResetToken(String emailAddress) {
        var user = userApplicationService.findUserByEmailAddress(emailAddress);
        var token = UUID.randomUUID().toString();
        userApplicationService.handleCreatePasswordResetTokenCommand(CreatePasswordResetTokenCommand.builder()
                .userId(user.id())
                .token(token)
                .build());
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", user.username());
        properties.put("passwordResetToken", token);
        var subject = "Password Reset";
        var template = "password-reset.html";
        emailUtil.sendEmail(emailAddress, subject, template, properties);
    }

    public void resetPasswordWithResetToken(ResetPasswordRequest resetPasswordRequest) {
        var user = userApplicationService.findUserByResetToken(resetPasswordRequest.token());
        passwordUtil.validatePassword(user, resetPasswordRequest.newPassword());
        var newPassword = bCryptPasswordEncoder.encode(resetPasswordRequest.newPassword());
        userApplicationService.handleUpdatePasswordCommand(UpdatePasswordCommand.builder()
                        .userId(user.id())
                        .newPassword(newPassword)
                .build());
    }
    public void logout(HttpServletRequest request) {
        var session = getSessionByToken(request);
        var terminatedSession = userApplicationService.terminateSession(session.getId().getValue());
    }

    private Session getSessionByToken(HttpServletRequest request) {
        final String authorization = request.getHeader("Authorization");
        final String token = Objects.requireNonNull(authorization).split(" ")[1].trim();
        return userApplicationService.findSessionByToken(token);
    }

    public String getRefreshToken(UUID userId, HttpServletRequest request){
        var userDto = userApplicationService.findUserById(userId);
        var session = getSessionByToken(request);
        //terminate session
        userApplicationService.terminateSession(session.getId().getValue());
        //create a new session
        var jwtToken = jwtUtil.generateJWTToken(userDto);
        var newSession = Session.builder()
                .token(jwtToken)
                .userId(userDto.id())
                .startedAt(Instant.now())
                .isActive(true)
                .expectedTerminationTime(jwtUtil.getTokenExpiry(jwtToken))
                .ipAddress(new IPAddress(request.getRemoteAddr()))
                .build();
        userApplicationService.createSession(newSession);
        //return new token
        return jwtToken;
    }
}