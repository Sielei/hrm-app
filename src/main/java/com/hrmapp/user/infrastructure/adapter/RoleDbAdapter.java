package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.dto.RoleDto;
import com.hrmapp.user.application.port.output.RoleRepository;
import com.hrmapp.user.domain.entity.Role;
import com.hrmapp.user.infrastructure.UserDataMapper;
import com.hrmapp.user.infrastructure.data.RoleJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public Role save(Role role) {
        var roleEntity = userDataMapper.mapRoleToRoleJpaEntity(role);
        var newRole = roleJpaRepository.save(roleEntity);
        return userDataMapper.mapRoleJpaEntityToRole(newRole);
    }

    @Override
    public Page<RoleDto> findRoles(PageRequest pageable) {
        return roleJpaRepository.findAll(pageable)
                .map(roleEntity -> RoleDto.fromEntity(userDataMapper.mapRoleJpaEntityToRole(roleEntity)));
    }

    @Override
    public void deleteRole(Role role) {
        var roleEntity = userDataMapper.mapRoleToRoleJpaEntity(role);
        roleJpaRepository.delete(roleEntity);
    }
}
