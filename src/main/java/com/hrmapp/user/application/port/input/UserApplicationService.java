package com.hrmapp.user.application.port.input;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.application.dto.PasswordPolicyDto;
import com.hrmapp.user.application.dto.command.CreatePasswordResetTokenCommand;
import com.hrmapp.user.application.dto.command.UpdatePasswordCommand;
import com.hrmapp.user.application.dto.response.CreatePasswordResetTokenResponse;
import com.hrmapp.user.application.port.input.handler.command.*;
import com.hrmapp.user.application.port.input.handler.query.*;
import com.hrmapp.user.domain.entity.Session;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserApplicationService {
    private final UserCommandHandler userCommandHandler;
    private final UserQueryHandler userQueryHandler;
    private final PasswordResetCommandHandler passwordResetCommandHandler;
    private final PasswordResetQueryHandler passwordResetQueryHandler;
    private final RoleCommandHandler roleCommandHandler;
    private final RoleQueryHandler roleQueryHandler;
    private final PasswordPolicyCommandHandler passwordPolicyCommandHandler;
    private final PasswordPolicyQueryHandler passwordPolicyQueryHandler;
    private final PermissionCommandHandler permissionCommandHandler;
    private final PermissionQueryHandler permissionQueryHandler;
    private final SessionCommandHandler sessionCommandHandler;
    private final SessionQueryHandler sessionQueryHandler;

    public UserApplicationService(UserCommandHandler userCommandHandler, UserQueryHandler userQueryHandler,
                                  PasswordResetCommandHandler passwordResetCommandHandler, PasswordResetQueryHandler passwordResetQueryHandler, RoleCommandHandler roleCommandHandler, RoleQueryHandler roleQueryHandler,
                                  PasswordPolicyCommandHandler passwordPolicyCommandHandler, PasswordPolicyQueryHandler passwordPolicyQueryHandler,
                                  PermissionCommandHandler permissionCommandHandler, PermissionQueryHandler permissionQueryHandler,
                                  SessionCommandHandler sessionCommandHandler, SessionQueryHandler sessionQueryHandler) {
        this.userCommandHandler = userCommandHandler;
        this.userQueryHandler = userQueryHandler;
        this.passwordResetCommandHandler = passwordResetCommandHandler;
        this.passwordResetQueryHandler = passwordResetQueryHandler;
        this.roleCommandHandler = roleCommandHandler;
        this.roleQueryHandler = roleQueryHandler;
        this.passwordPolicyCommandHandler = passwordPolicyCommandHandler;
        this.passwordPolicyQueryHandler = passwordPolicyQueryHandler;
        this.permissionCommandHandler = permissionCommandHandler;
        this.permissionQueryHandler = permissionQueryHandler;
        this.sessionCommandHandler = sessionCommandHandler;
        this.sessionQueryHandler = sessionQueryHandler;
    }
    public UserDto findUserById(UUID userId) {
        return userQueryHandler.findById(userId);
    }

    public UserDto findUserByUsername(String username) {
        return userQueryHandler.findByUsername(username);
    }

    public Session createSession(Session session) {
        return sessionCommandHandler.handleCreateSessionCommand(session);
    }

    public Session findSessionByToken(String token) {
        return sessionQueryHandler.findSessionByToken(token);
    }

    public UserDto findUserByEmailAddress(String email) {
        return userQueryHandler.findByEmailAddress(email);
    }

    public CreatePasswordResetTokenResponse handleCreatePasswordResetTokenCommand(CreatePasswordResetTokenCommand createPasswordResetTokenCommand) {
        return passwordResetCommandHandler.handleCreatePasswordResetTokenCommand(createPasswordResetTokenCommand);
    }

    public UserDto findUserByResetToken(String token) {
        var passwordReset = passwordResetQueryHandler.findPasswordResetByToken(token);
        return findUserById(passwordReset.getUserId().getValue());
    }

    public PasswordPolicyDto findPasswordPolicyById(UUID policyId) {
        return passwordPolicyQueryHandler.findPasswordPolicyById(policyId);
    }

    public void handleUpdatePasswordCommand(UpdatePasswordCommand updatePasswordCommand) {
        userCommandHandler.handleUpdatePasswordCommand(updatePasswordCommand);
    }

    public Session terminateSession(UUID sessionId) {
        var session = sessionQueryHandler.findSessionById(sessionId);
        return sessionCommandHandler.terminateSession(session);
    }
}
