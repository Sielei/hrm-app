package com.hrmapp.user.infrastructure;

import com.hrmapp.user.domain.entity.Permission;
import com.hrmapp.user.domain.entity.Role;
import com.hrmapp.user.domain.entity.User;
import com.hrmapp.user.infrastructure.data.PermissionEntity;
import com.hrmapp.user.infrastructure.data.RoleEntity;
import com.hrmapp.user.infrastructure.data.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDataMapper {
    public static User mapUserJpaEntityToUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .emailAddress(userEntity.getEmailAddress())
                .password(userEntity.getPassword())
                .employeeId(userEntity.getEmployeeId())
                .status(userEntity.getStatus())
                .passwordPolicyId(userEntity.getPasswordPolicy().getId())
                .changePasswordNextLogin(userEntity.getChangePasswordNextLogin())
                .roles(mapRoleEntitiesToRoles(userEntity.getRoles()))
                .build();
    }

    private static Set<Role> mapRoleEntitiesToRoles(Set<RoleEntity> roleEntities) {
        return roleEntities.stream().map(UserDataMapper::mapRoleJpaEntityToRole)
                .collect(Collectors.toSet());
    }
    public static Role mapRoleJpaEntityToRole(RoleEntity roleEntity){
        return Role.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .description(roleEntity.getDescription())
                .permissions(mapPermissionJpaEntitiesToPermissions(roleEntity.getPermissions()))
                .build();
    }

    private static Set<Permission> mapPermissionJpaEntitiesToPermissions(Set<PermissionEntity> permissionEntities) {
        return permissionEntities.stream().map(UserDataMapper::mapPermissionJpaEntityToPermission)
                .collect(Collectors.toSet());
    }

    private static Permission mapPermissionJpaEntityToPermission(PermissionEntity permissionEntity) {
        return Permission.builder()
                .id(permissionEntity.getId())
                .name(permissionEntity.getName())
                .subject(permissionEntity.getSubject())
                .permission(permissionEntity.getPermission())
                .action(permissionEntity.getAction())
                .authority(permissionEntity.getAuthority())
                .build();
    }
}
