package com.hrmapp.user.application.port.input.handler.command;

import com.hrmapp.user.application.dto.RoleDto;
import com.hrmapp.user.application.dto.RolePermission;
import com.hrmapp.user.application.dto.request.CreateRoleRequest;
import com.hrmapp.user.application.dto.request.UpdateRoleRequest;
import com.hrmapp.user.application.port.output.PermissionRepository;
import com.hrmapp.user.application.port.output.RoleRepository;
import com.hrmapp.user.domain.UserDomainService;
import com.hrmapp.user.domain.entity.Permission;
import com.hrmapp.user.domain.entity.Role;
import com.hrmapp.user.domain.exception.PermissionException;
import com.hrmapp.user.domain.exception.RoleException;
import com.hrmapp.user.domain.exception.UserDomainException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Transactional
public class RoleCommandHandler {
    private final RoleRepository roleRepository;
    private final UserDomainService userDomainService;
    private final PermissionRepository permissionRepository;

    public RoleCommandHandler(RoleRepository roleRepository, UserDomainService userDomainService, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.userDomainService = userDomainService;
        this.permissionRepository = permissionRepository;
    }

    public RoleDto handleCreateRoleRequest(CreateRoleRequest createRoleRequest, UUID userId) {
        var role = createRoleRequest.toEntity(userId);
        userDomainService.createRole(role);
        var persistedRole = roleRepository.save(role);
        return RoleDto.fromEntity(persistedRole);
    }

    public RoleDto handleAddPermissions(UUID roleId, Set<RolePermission> rolePermissions) {
        var role = findRoleById(roleId);
        var permissions = rolePermissions.stream()
                .map(rolePermission -> findPermissionById(rolePermission.permissionId()))
                .collect(Collectors.toSet());
        userDomainService.addPermissionsToRole(role, permissions);
        var updatedRole = roleRepository.save(role);
        return RoleDto.fromEntity(updatedRole);
    }

    private Role findRoleById(UUID roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleException("Role with id: " + roleId + " does not exist!"));
    }
    private Permission findPermissionById(UUID permissionId) {
        return permissionRepository.findById(permissionId)
                .orElseThrow(() -> new PermissionException("Permission with id:" +
                        permissionId + " not found!"));
    }

    public RoleDto handleRemoveRolePermissions(UUID roleId, Set<RolePermission> rolePermissions) {
        var role = findRoleById(roleId);
        var permissions = rolePermissions.stream()
                .map(rolePermission -> findPermissionById(rolePermission.permissionId()))
                .collect(Collectors.toSet());
        userDomainService.removeRolePermissions(role, permissions);
        var updatedRole = roleRepository.save(role);
        return RoleDto.fromEntity(updatedRole);
    }

    public RoleDto handleUpdateRoleRequest(UUID roleId, UpdateRoleRequest updateRoleRequest) {
        var role = findRoleById(roleId);
        userDomainService.updateRole(role, updateRoleRequest.name(), updateRoleRequest.description());
        var updatedRole = roleRepository.save(role);
        return RoleDto.fromEntity(updatedRole);
    }

    public void handleDeleteRole(UUID roleId) {
        var role = findRoleById(roleId);
        roleRepository.deleteRole(role);
    }
}
