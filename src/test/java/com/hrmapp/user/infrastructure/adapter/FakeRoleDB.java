package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.dto.RoleDto;
import com.hrmapp.user.application.port.output.RoleRepository;
import com.hrmapp.user.domain.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

public class FakeRoleDB implements RoleRepository {
    @Override
    public Optional<Role> findById(UUID roleId) {
        return Optional.empty();
    }

    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public Page<RoleDto> findRoles(PageRequest pageable) {
        return null;
    }

    @Override
    public void deleteRole(Role role) {

    }
}
