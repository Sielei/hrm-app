package com.hrmapp.user.application.port.output;

import com.hrmapp.user.domain.entity.Permission;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository {
    Optional<Permission> findById(UUID permissionId);
}
