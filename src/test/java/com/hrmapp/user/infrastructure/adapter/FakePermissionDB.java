package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.dto.PermissionDto;
import com.hrmapp.user.application.port.output.PermissionRepository;
import com.hrmapp.user.domain.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public class FakePermissionDB implements PermissionRepository {
    @Override
    public Optional<Permission> findById(UUID permissionId) {
        return Optional.empty();
    }

    @Override
    public Page<PermissionDto> findPermissions(Pageable pageable) {
        return null;
    }
}
