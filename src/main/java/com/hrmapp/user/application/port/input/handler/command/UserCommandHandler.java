package com.hrmapp.user.application.port.input.handler.command;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.application.dto.UserRole;
import com.hrmapp.user.application.dto.command.CreateUserCommand;
import com.hrmapp.user.application.dto.command.UpdatePasswordCommand;
import com.hrmapp.user.application.dto.request.DeactivateUserRequest;
import com.hrmapp.user.application.dto.request.UpdatePasswordRequest;
import com.hrmapp.user.application.dto.request.UpdateUserRequest;
import com.hrmapp.user.application.dto.response.CreateUserResponse;
import com.hrmapp.user.application.port.output.RoleRepository;
import com.hrmapp.user.application.port.output.UserRepository;
import com.hrmapp.user.domain.UserDomainService;
import com.hrmapp.user.domain.entity.Role;
import com.hrmapp.user.domain.entity.User;
import com.hrmapp.user.domain.exception.UserDomainException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Transactional
public class UserCommandHandler {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDomainService userDomainService;

    public UserCommandHandler(UserRepository userRepository, RoleRepository roleRepository, UserDomainService userDomainService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userDomainService = userDomainService;
    }

    public void handleUpdatePasswordCommand(UpdatePasswordCommand updatePasswordCommand) {
        var user = findUserById(updatePasswordCommand.userId());
        userDomainService.updateUserPassword(user, updatePasswordCommand.newPassword());
        var updatedUser = userRepository.save(user);
    }

    public UserDto handleCreateUserCommand(CreateUserCommand createUserCommand) {
        var user = createUserCommand.toUser();
        userDomainService.createUser(user);
        var persistedUser = userRepository.save(user);
        return UserDto.fromEntity(persistedUser);
    }

    public CreateUserResponse handleUpdateUserRequest(UUID userId, UpdateUserRequest updateUserRequest) {
        var user = findUserById(userId);
        userDomainService.updateUser(user, updateUserRequest.username(), updateUserRequest.emailAddress());
        var updatedUser = userRepository.save(user);
        return CreateUserResponse.fromDto(UserDto.fromEntity(updatedUser));
    }

    private User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserDomainException("User with id:" +
                        userId + " not found!"));
    }

    public CreateUserResponse handleAssignRoles(UUID userId, List<UserRole> userRoles) {
        var user = findUserById(userId);
        var roles = userRoles.stream()
                .map(userRole -> findRoleById(userRole.roleId()))
                .collect(Collectors.toSet());
        userDomainService.assignRoles(user, roles);
        var updatedUser = userRepository.save(user);
        return CreateUserResponse.fromDto(UserDto.fromEntity(updatedUser));
    }

    private Role findRoleById(UUID roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new UserDomainException("Role with id:" +
                        roleId + " not found!"));
    }

    public CreateUserResponse handleRemoveRoles(UUID userId, List<UserRole> userRoles) {
        var user = findUserById(userId);
        var rolesToRemove = userRoles.stream()
                .map(userRole -> findRoleById(userRole.roleId()))
                .collect(Collectors.toSet());
        userDomainService.removeRoles(user, rolesToRemove);
        var updatedUser = userRepository.save(user);
        return CreateUserResponse.fromDto(UserDto.fromEntity(updatedUser));
    }

    public CreateUserResponse handleDeactivateUserRequest(UUID deactivatedBy, DeactivateUserRequest deactivateUserRequest) {
        var user = findUserById(deactivateUserRequest.userId());
        userDomainService.deactivateUser(user);
        var updatedUser = userRepository.save(user);
        createDeactivationRecord(updatedUser, deactivatedBy);
        return CreateUserResponse.fromDto(UserDto.fromEntity(updatedUser));
    }

    private void createDeactivationRecord(User updatedUser, UUID deactivatedBy) {
    }
}
