package com.hrmapp.user.infrastructure.data;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<PermissionEntity> permissions;
    private UUID createdBy;
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;

    public RoleEntity() {
    }

    private RoleEntity(Builder builder) {
        id = builder.id;
        name = builder.name;
        description = builder.description;
        permissions = builder.permissions;
        createdBy = builder.createdBy;
        users = builder.users;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<PermissionEntity> getPermissions() {
        return permissions;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }


    public static final class Builder {
        private UUID id;
        private String name;
        private String description;
        private Set<PermissionEntity> permissions;
        private UUID createdBy;
        private Set<UserEntity> users;

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

        public Builder permissions(Set<PermissionEntity> val) {
            permissions = val;
            return this;
        }

        public Builder createdBy(UUID val) {
            createdBy = val;
            return this;
        }

        public Builder users(Set<UserEntity> val) {
            users = val;
            return this;
        }

        public RoleEntity build() {
            return new RoleEntity(this);
        }
    }
}