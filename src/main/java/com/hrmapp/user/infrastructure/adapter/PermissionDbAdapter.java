package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.port.output.PermissionRepository;
import com.hrmapp.user.infrastructure.data.PermissionJpaRepository;

public class PermissionDbAdapter implements PermissionRepository {
    private final PermissionJpaRepository permissionJpaRepository;

    public PermissionDbAdapter(PermissionJpaRepository permissionJpaRepository) {
        this.permissionJpaRepository = permissionJpaRepository;
    }
}
