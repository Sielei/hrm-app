package com.hrmapp.user.infrastructure.data;

import com.hrmapp.user.application.dto.PermissionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, UUID> {
    @Query("""
            select new com.hrmapp.user.application.dto.PermissionDto(p.id, p.name, p.subject, p.permission,
            p.authority, p.action)
            from PermissionEntity p
           """)
    Page<PermissionDto> findPermissions(Pageable pageable);
}