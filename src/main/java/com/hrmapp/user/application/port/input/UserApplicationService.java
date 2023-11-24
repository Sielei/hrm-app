package com.hrmapp.user.application.port.input;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.common.application.port.input.util.PasswordUtil;
import com.hrmapp.user.application.dto.*;
import com.hrmapp.user.application.dto.command.CreatePasswordResetTokenCommand;
import com.hrmapp.user.application.dto.command.CreateUserCommand;
import com.hrmapp.user.application.dto.request.*;
import com.hrmapp.user.application.dto.command.UpdatePasswordCommand;
import com.hrmapp.user.application.dto.response.CreatePasswordResetTokenResponse;
import com.hrmapp.user.application.dto.response.CreateUserResponse;
import com.hrmapp.user.application.port.input.handler.command.*;
import com.hrmapp.user.application.port.input.handler.query.*;
import com.hrmapp.user.domain.entity.Session;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
    private final UserEmailHandler userEmailHandler;

    public UserApplicationService(UserCommandHandler userCommandHandler, UserQueryHandler userQueryHandler,
                                  PasswordResetCommandHandler passwordResetCommandHandler, PasswordResetQueryHandler passwordResetQueryHandler, RoleCommandHandler roleCommandHandler, RoleQueryHandler roleQueryHandler,
                                  PasswordPolicyCommandHandler passwordPolicyCommandHandler, PasswordPolicyQueryHandler passwordPolicyQueryHandler,
                                  PermissionCommandHandler permissionCommandHandler, PermissionQueryHandler permissionQueryHandler,
                                  SessionCommandHandler sessionCommandHandler, SessionQueryHandler sessionQueryHandler, UserEmailHandler userEmailHandler) {
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
        this.userEmailHandler = userEmailHandler;
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

    public CreateUserResponse handleCreateUserCommand(CreateUserCommand createUserCommand) {
        var userDto = userCommandHandler.handleCreateUserCommand(createUserCommand);
        userEmailHandler.sendWelcomeEmail(userDto, createUserCommand.genPassword());
        return CreateUserResponse.fromDto(userDto);
    }

    public CreateUserResponse handleUpdateUserRequest(UUID userId, UpdateUserRequest updateUserRequest) {
        return userCommandHandler.handleUpdateUserRequest(userId, updateUserRequest);
    }

    public CreateUserResponse handleAssignRoles(UUID userId, List<UserRole> userRoles) {
        return userCommandHandler.handleAssignRoles(userId, userRoles);
    }

    public CreateUserResponse handleRemoveRoles(UUID userId, List<UserRole> userRoles) {
        return userCommandHandler.handleRemoveRoles(userId, userRoles);
    }

    public CreateUserResponse findUserByUserId(UUID userId) {
        return CreateUserResponse.fromDto(findUserById(userId));
    }

    public CreateUserResponse handleDeactivateUserRequest(UUID deactivatedBy, DeactivateUserRequest deactivateUserRequest) {
        return userCommandHandler.handleDeactivateUserRequest(deactivatedBy, deactivateUserRequest);
    }

    public PagedResult<CreateUserResponse> findAllUsers(PageQuery pageQuery) {
        return userQueryHandler.findAllUsers(pageQuery);
    }

    public CreatePasswordPolicyResponse handleCreatePasswordPolicy(CreatePasswordPolicyRequest createPasswordPolicyRequest) {
        return passwordPolicyCommandHandler.handleCreatePasswordPolicy(createPasswordPolicyRequest);
    }

    public PagedResult<PasswordPolicyDto> findAllPasswordPolicies(PageQuery pageQuery) {
        return passwordPolicyQueryHandler.findAllPasswordPolicies(pageQuery);
    }

    public PasswordPolicyDto handleUpdatePasswordPolicyRequest(UUID passwordPolicyId, UpdatePasswordPolicyRequest updatePasswordPolicyRequest) {
        return passwordPolicyCommandHandler.handleUpdatePasswordPolicyRequest(passwordPolicyId, updatePasswordPolicyRequest);
    }

    public void handleDeletePasswordPolicy(UUID passwordPolicyId) {
        passwordPolicyCommandHandler.handleDeletePasswordPolicy(passwordPolicyId);
    }

    public RoleDto handleCreateRoleRequest(CreateRoleRequest createRoleRequest, UUID userId) {
        return roleCommandHandler.handleCreateRoleRequest(createRoleRequest, userId);
    }

    public RoleDto findRoleById(UUID roleId) {
        return roleQueryHandler.findRoleById(roleId);
    }

    public RoleDto handleAddPermissions(UUID roleId, Set<RolePermission> rolePermissions) {
        return roleCommandHandler.handleAddPermissions(roleId, rolePermissions);
    }

    public RoleDto handleRemoveRolePermissions(UUID roleId, Set<RolePermission> rolePermissions) {
        return roleCommandHandler.handleRemoveRolePermissions(roleId, rolePermissions);
    }

    public RoleDto handleUpdateRoleRequest(UUID roleId, UpdateRoleRequest updateRoleRequest) {
        return roleCommandHandler.handleUpdateRoleRequest(roleId, updateRoleRequest);
    }

    public PagedResult<RoleDto> findAllRoles(PageQuery pageQuery) {
        return roleQueryHandler.findAllRoles(pageQuery);
    }

    public void handleDeleteRole(UUID roleId) {
        roleCommandHandler.handleDeleteRole(roleId);
    }

    public PagedResult<PermissionDto> findAllPermissions(PageQuery pageQuery) {
        return permissionQueryHandler.findAllPermissions(pageQuery);
    }
}
