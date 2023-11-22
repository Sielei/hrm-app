package com.hrmapp.user.application.port.output;

import com.hrmapp.user.application.dto.RoleDto;
import com.hrmapp.user.domain.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {
    Optional<Role> findById(UUID roleId);

    Role save(Role role);

    Page<RoleDto> findRoles(PageRequest pageable);

    void deleteRole(Role role);
}
