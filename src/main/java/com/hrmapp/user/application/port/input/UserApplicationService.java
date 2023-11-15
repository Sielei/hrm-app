package com.hrmapp.user.application.port.input;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.application.port.input.handler.command.*;
import com.hrmapp.user.application.port.input.handler.query.*;
import com.hrmapp.user.domain.entity.Session;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService {
    private final UserCommandHandler userCommandHandler;
    private final UserQueryHandler userQueryHandler;
    private final RoleCommandHandler roleCommandHandler;
    private final RoleQueryHandler roleQueryHandler;
    private final PasswordPolicyCommandHandler passwordPolicyCommandHandler;
    private final PasswordPolicyQueryHandler passwordPolicyQueryHandler;
    private final PermissionCommandHandler permissionCommandHandler;
    private final PermissionQueryHandler permissionQueryHandler;
    private final SessionCommandHandler sessionCommandHandler;
    private final SessionQueryHandler sessionQueryHandler;

    public UserApplicationService(UserCommandHandler userCommandHandler, UserQueryHandler userQueryHandler,
                                  RoleCommandHandler roleCommandHandler, RoleQueryHandler roleQueryHandler,
                                  PasswordPolicyCommandHandler passwordPolicyCommandHandler, PasswordPolicyQueryHandler passwordPolicyQueryHandler,
                                  PermissionCommandHandler permissionCommandHandler, PermissionQueryHandler permissionQueryHandler,
                                  SessionCommandHandler sessionCommandHandler, SessionQueryHandler sessionQueryHandler) {
        this.userCommandHandler = userCommandHandler;
        this.userQueryHandler = userQueryHandler;
        this.roleCommandHandler = roleCommandHandler;
        this.roleQueryHandler = roleQueryHandler;
        this.passwordPolicyCommandHandler = passwordPolicyCommandHandler;
        this.passwordPolicyQueryHandler = passwordPolicyQueryHandler;
        this.permissionCommandHandler = permissionCommandHandler;
        this.permissionQueryHandler = permissionQueryHandler;
        this.sessionCommandHandler = sessionCommandHandler;
        this.sessionQueryHandler = sessionQueryHandler;
    }

    public UserDto findUserByUsername(String username) {
        return userQueryHandler.findByUsername(username);
    }

    public void createSession(Session session) {}

    public Session findSessionByToken(String token) {
        return null;
    }
}
