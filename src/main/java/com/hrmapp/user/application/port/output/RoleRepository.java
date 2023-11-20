package com.hrmapp.user.application.port.output;

import com.hrmapp.user.domain.entity.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {
    Optional<Role> findById(UUID roleId);
}
