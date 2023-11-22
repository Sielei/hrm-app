package com.hrmapp.user.application.port.input.handler.query;

import com.hrmapp.user.application.dto.PageQuery;
import com.hrmapp.user.application.dto.PagedResult;
import com.hrmapp.user.application.dto.RoleDto;
import com.hrmapp.user.application.port.output.RoleRepository;
import com.hrmapp.user.domain.exception.RoleException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Transactional
public class RoleQueryHandler {
    private final RoleRepository roleRepository;

    public RoleQueryHandler(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleDto findRoleById(UUID roleId) {
        return roleRepository.findById(roleId)
                .map(RoleDto::fromEntity)
                .orElseThrow(() -> new RoleException("Role with id: " + roleId + " does not exist!"));
    }

    public PagedResult<RoleDto> findAllRoles(PageQuery pageQuery) {
        var pageNo = pageQuery.pageNo() > 0 ? pageQuery.pageNo() - 1 : 0;
        var pageable = PageRequest.of(pageNo, pageQuery.pageSize());
        var page = roleRepository.findRoles(pageable);
        return new PagedResult<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
