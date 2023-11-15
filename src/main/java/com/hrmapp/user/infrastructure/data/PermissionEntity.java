package com.hrmapp.user.infrastructure.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "permissions")
public class PermissionEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    private String name;
    private String subject;
    private String permission;
    private String authority;
    private String  action;

    public PermissionEntity() {
    }

    private PermissionEntity(Builder builder) {
        id = builder.id;
        name = builder.name;
        subject = builder.subject;
        permission = builder.permission;
        authority = builder.authority;
        action = builder.action;
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

    public String getSubject() {
        return subject;
    }

    public String getPermission() {
        return permission;
    }

    public String getAuthority() {
        return authority;
    }

    public String getAction() {
        return action;
    }


    public static final class Builder {
        private UUID id;
        private String name;
        private String subject;
        private String permission;
        private String authority;
        private String action;

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

        public Builder subject(String val) {
            subject = val;
            return this;
        }

        public Builder permission(String val) {
            permission = val;
            return this;
        }

        public Builder authority(String val) {
            authority = val;
            return this;
        }

        public Builder action(String val) {
            action = val;
            return this;
        }

        public PermissionEntity build() {
            return new PermissionEntity(this);
        }
    }
}