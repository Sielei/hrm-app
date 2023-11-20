package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.port.output.RoleRepository;
import com.hrmapp.user.domain.entity.Role;
import com.hrmapp.user.infrastructure.UserDataMapper;
import com.hrmapp.user.infrastructure.data.RoleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RoleDbAdapter implements RoleRepository {
    private final RoleJpaRepository roleJpaRepository;
    private final UserDataMapper userDataMapper;

    public RoleDbAdapter(RoleJpaRepository roleJpaRepository, UserDataMapper userDataMapper) {
        this.roleJpaRepository = roleJpaRepository;
        this.userDataMapper = userDataMapper;
    }

    @Override
    public Optional<Role> findById(UUID roleId) {
        return roleJpaRepository.findById(roleId)
                .map(userDataMapper::mapRoleJpaEntityToRole);
    }
}
