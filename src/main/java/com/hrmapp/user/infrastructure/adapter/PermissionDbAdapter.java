package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.dto.PermissionDto;
import com.hrmapp.user.application.port.output.PermissionRepository;
import com.hrmapp.user.domain.entity.Permission;
import com.hrmapp.user.infrastructure.UserDataMapper;
import com.hrmapp.user.infrastructure.data.PermissionJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public class PermissionDbAdapter implements PermissionRepository {
    private final PermissionJpaRepository permissionJpaRepository;
    private final UserDataMapper userDataMapper;

    public PermissionDbAdapter(PermissionJpaRepository permissionJpaRepository, UserDataMapper userDataMapper) {
        this.permissionJpaRepository = permissionJpaRepository;
        this.userDataMapper = userDataMapper;
    }

    @Override
    public Optional<Permission> findById(UUID permissionId) {
        return permissionJpaRepository.findById(permissionId)
                .map(userDataMapper::mapPermissionJpaEntityToPermission);
    }

    @Override
    public Page<PermissionDto> findPermissions(Pageable pageable) {
        return permissionJpaRepository.findPermissions(pageable);
    }
}
