package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.port.output.RoleRepository;
import com.hrmapp.user.infrastructure.data.RoleJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class RoleDbAdapter implements RoleRepository {
    private final RoleJpaRepository roleJpaRepository;

    public RoleDbAdapter(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }
}
