package com.hrmapp.user.application.port.input.handler.query;

import com.hrmapp.user.application.dto.PageQuery;
import com.hrmapp.user.application.dto.PagedResult;
import com.hrmapp.user.application.dto.PermissionDto;
import com.hrmapp.user.application.port.output.PermissionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PermissionQueryHandler {
    private final PermissionRepository permissionRepository;

    public PermissionQueryHandler(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public PagedResult<PermissionDto> findAllPermissions(PageQuery pageQuery) {
        var pageNo = pageQuery.pageNo() > 0 ? pageQuery.pageNo() - 1 : 0;
        var pageable = PageRequest.of(pageNo, pageQuery.pageSize());
        var page = permissionRepository.findPermissions(pageable);
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
