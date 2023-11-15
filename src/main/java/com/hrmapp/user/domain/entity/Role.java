package com.hrmapp.user.domain.entity;

import com.hrmapp.common.domain.entity.BaseEntity;
import com.hrmapp.user.domain.exception.PermissionException;
import com.hrmapp.user.domain.valueobject.RoleId;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class Role extends BaseEntity<RoleId> {
    private String name;
    private String description;
    private Set<Permission> permissions;
    private User createdBy;

    private Role(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        description = builder.description;
        permissions = builder.permissions;
        createdBy = builder.createdBy;
    }
    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void createRole(){

        setId(new RoleId(UUID.randomUUID()));
        permissions = Collections.emptySet();
    }
    public void updateRole(String name, String description){
        this.name = name;
        this.description = description;
    }

    public void addPermissions(Set<Permission> permissions){
        if (this.permissions == null){
            this.permissions = Collections.emptySet();
        }
        this.permissions.addAll(permissions);
    }
    public void removePermission(Set<Permission> permissions){
        if (this.permissions == null || this.permissions.isEmpty()){
            throw new PermissionException("Trying to remove permissions that do not exist for this role!");
        }
        this.permissions.removeAll(permissions);
    }

    public void assignPermissions(Set<Permission> permissions){
        this.permissions = permissions;
    }

    public static final class Builder {
        private RoleId id;
        private String name;
        private String description;
        private Set<Permission> permissions;
        private User createdBy;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = new RoleId(val);
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

        public Builder createdBy(User val) {
            createdBy = val;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }
}
