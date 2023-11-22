package com.hrmapp.user.application.dto;

import com.hrmapp.user.domain.entity.Permission;
import com.hrmapp.user.domain.entity.Role;

import java.util.Set;
import java.util.UUID;

public record RoleDto(UUID id, String name, String description, Set<Permission> permissions, UUID createdBy) {
    public static Builder builder() {
        return new Builder();
    }
    public static RoleDto fromEntity(Role role) {
        return RoleDto.builder()
                .id(role.getId().getValue())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(role.getPermissions())
                .createdBy(role.getCreatedBy().getValue())
                .build();
    }


    public static final class Builder {
        private UUID id;
        private String name;
        private String description;
        private Set<Permission> permissions;
        private UUID createdBy;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder permissions(Set<Permission> val) {
            permissions = val;
            return this;
        }

        public Builder createdBy(UUID val) {
            createdBy = val;
            return this;
        }

        public RoleDto build() {
            return new RoleDto(id, name, description, permissions, createdBy);
        }
    }
}
