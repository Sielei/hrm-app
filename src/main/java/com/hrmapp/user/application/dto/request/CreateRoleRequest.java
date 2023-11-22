package com.hrmapp.user.application.dto.request;

import com.hrmapp.user.domain.entity.Role;

import java.util.UUID;

public record CreateRoleRequest(String name, String description) {
    public Role toEntity(UUID userId) {
        return Role.builder()
                .name(name)
                .description(description)
                .createdBy(userId)
                .build();
    }
}
