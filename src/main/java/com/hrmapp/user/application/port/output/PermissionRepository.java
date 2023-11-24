package com.hrmapp.user.application.port.output;

import com.hrmapp.user.application.dto.PermissionDto;
import com.hrmapp.user.domain.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository {
    Optional<Permission> findById(UUID permissionId);

    Page<PermissionDto> findPermissions(Pageable pageable);
}
