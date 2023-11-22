package com.hrmapp.user.infrastructure;

import com.hrmapp.user.domain.entity.*;
import com.hrmapp.user.domain.valueobject.IPAddress;
import com.hrmapp.user.infrastructure.data.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDataMapper {
    private final UserJpaRepository userJpaRepository ;
    private final PasswordPolicyJpaRepository passwordPolicyJpaRepository;

    public UserDataMapper(UserJpaRepository userJpaRepository, PasswordPolicyJpaRepository passwordPolicyJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.passwordPolicyJpaRepository = passwordPolicyJpaRepository;
    }

    public User mapUserJpaEntityToUser(UserEntity userEntity) {
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

    private Set<Role> mapRoleEntitiesToRoles(Set<RoleEntity> roleEntities) {
        return roleEntities.stream().map(this::mapRoleJpaEntityToRole)
                .collect(Collectors.toSet());
    }
    public Role mapRoleJpaEntityToRole(RoleEntity roleEntity){
        return Role.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .description(roleEntity.getDescription())
                .permissions(mapPermissionJpaEntitiesToPermissions(roleEntity.getPermissions()))
                .build();
    }

    private Set<Permission> mapPermissionJpaEntitiesToPermissions(Set<PermissionEntity> permissionEntities) {
        return permissionEntities.stream().map(this::mapPermissionJpaEntityToPermission)
                .collect(Collectors.toSet());
    }

    private Permission mapPermissionJpaEntityToPermission(PermissionEntity permissionEntity) {
        return Permission.builder()
                .id(permissionEntity.getId())
                .name(permissionEntity.getName())
                .subject(permissionEntity.getSubject())
                .permission(permissionEntity.getPermission())
                .action(permissionEntity.getAction())
                .authority(permissionEntity.getAuthority())
                .build();
    }

    public PasswordResetEntity mapPasswordResetToPasswordResetJpaEntity(PasswordReset passwordReset) {
        return PasswordResetEntity.builder()
                .id(passwordReset.getId().getValue())
                .user(userJpaRepository.findById(passwordReset.getUserId().getValue()).get())
                .token(passwordReset.getToken())
                .expiry(passwordReset.getExpiry())
                .build();
    }

    public PasswordReset mapPasswordResetJpaEntityToPasswordReset(PasswordResetEntity passwordResetEntity) {
        return PasswordReset.builder()
                .id(passwordResetEntity.getId())
                .userId(passwordResetEntity.getUser().getId())
                .token(passwordResetEntity.getToken())
                .expiry(passwordResetEntity.getExpiry())
                .build();
    }

    public PasswordPolicy mapPasswordPolicyJpaEntityToPasswordPolicy(PasswordPolicyEntity passwordPolicyEntity) {
        return PasswordPolicy.builder()
                .id(passwordPolicyEntity.getId())
                .name(passwordPolicyEntity.getName())
                .passwordResetDays(passwordPolicyEntity.getPasswordResetDays())
                .numberOfCharacters(passwordPolicyEntity.getNumberOfCharacters())
                .numberOfSpecialCharacters(passwordPolicyEntity.getNumberOfSpecialCharacters())
                .numberOfNumericCharacters(passwordPolicyEntity.getNumberOfNumericCharacters())
                .numberOfLowercaseCharacters(passwordPolicyEntity.getNumberOfLowercaseCharacters())
                .numberOfUppercaseCharacters(passwordPolicyEntity.getNumberOfUppercaseCharacters())
                .build();
    }

    public UserEntity mapUserToUserJpaEntity(User user) {
        return UserEntity.builder()
                .id(user.getId().getValue())
                .employeeId(user.getEmployeeId().getValue())
                .username(user.getUsername())
                .emailAddress(user.getEmailAddress())
                .password(user.getPassword())
                .status(user.getStatus())
                .roles(mapRolesToRoleEntities(user.getRoles()))
                //.createdBy(user.getCreatedBy().getValue())
                .passwordPolicy(passwordPolicyJpaRepository.findById(
                        user.getPasswordPolicyId().getValue()).get())
                .changePasswordNextLogin(user.getChangePasswordNextLogin())
                .build();
    }

    private Set<RoleEntity> mapRolesToRoleEntities(Set<Role> roles) {
        return roles.stream()
                .map(this::mapRoleToRoleEntity)
                .collect(Collectors.toSet());
    }

    private  RoleEntity mapRoleToRoleEntity(Role role) {
        return RoleEntity.builder()
                .id(role.getId().getValue())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(mapPermissionsToPermissionJpaEntities(role.getPermissions()))
                .build();
    }

    private Set<PermissionEntity> mapPermissionsToPermissionJpaEntities(Set<Permission> permissions) {
        return permissions.stream()
                .map(this::mapPermissionToPermissionJpaEntity)
                .collect(Collectors.toSet());
    }

    private PermissionEntity mapPermissionToPermissionJpaEntity(Permission permission) {
        return PermissionEntity.builder()
                .id(permission.getId().getValue())
                .name(permission.getName())
                .subject(permission.getSubject())
                .permission(permission.getPermission())
                .action(permission.getAction())
                .authority(permission.getAuthority())
                .build();
    }

    public SessionEntity mapSessionToSessionJpaEntity(Session session) {
        return SessionEntity.builder()
                .id(session.getId().getValue())
                .userId(session.getUserId().getValue())
                .token(session.getToken())
                .ipAddress(session.getIpAddress().value())
                .startedAt(session.getStartedAt())
                .isActive(session.getActive())
                .expectedTerminationTime(session.getExpectedTerminationTime())
                .terminatedAt(session.getTerminatedAt())
                .build();
    }

    public Session mapSessionJpaEntityToSession(SessionEntity sessionEntity) {
        return Session.builder()
                .id(sessionEntity.getId())
                .userId(sessionEntity.getUserId())
                .token(sessionEntity.getToken())
                .ipAddress(new IPAddress(sessionEntity.getIpAddress()))
                .startedAt(sessionEntity.getStartedAt())
                .isActive(sessionEntity.getActive())
                .expectedTerminationTime(sessionEntity.getExpectedTerminationTime())
                .terminatedAt(sessionEntity.getTerminatedAt())
                .build();
    }

    public PasswordPolicyEntity mapPasswordPolicyToPasswordPolicyJpaEntity(PasswordPolicy passwordPolicy) {
        return PasswordPolicyEntity.builder()
                .id(passwordPolicy.getId().getValue())
                .name(passwordPolicy.getName())
                .passwordResetDays(passwordPolicy.getPasswordResetDays())
                .numberOfCharacters(passwordPolicy.getNumberOfCharacters())
                .numberOfSpecialCharacters(passwordPolicy.getNumberOfSpecialCharacters())
                .numberOfLowercaseCharacters(passwordPolicy.getNumberOfLowercaseCharacters())
                .numberOfNumericCharacters(passwordPolicy.getNumberOfNumericCharacters())
                .numberOfUppercaseCharacters(passwordPolicy.getNumberOfUppercaseCharacters())
                .build();
    }

    public RoleEntity mapRoleToRoleJpaEntity(Role role) {
        return RoleEntity.builder()
                .id(role.getId().getValue())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(mapPermissionsToPermissionJpaEntities(role.getPermissions()))
                .createdBy(role.getCreatedBy().getValue())
                .build();
    }
}
