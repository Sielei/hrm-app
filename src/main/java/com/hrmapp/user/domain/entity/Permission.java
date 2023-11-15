package com.hrmapp.user.domain.entity;

import com.hrmapp.common.domain.entity.BaseEntity;
import com.hrmapp.user.domain.valueobject.PermissionId;

import java.util.UUID;

public class Permission extends BaseEntity<PermissionId> {
    private String name;
    private String subject;
    private String permission;
    private String authority;
    private String  action;

    private Permission(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        subject = builder.subject;
        permission = builder.permission;
        authority = builder.authority;
        action = builder.action;
    }
    public static Builder builder() {
        return new Builder();
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

    public void createPermission(){
        setId(new PermissionId(UUID.randomUUID()));
    }
    public void updatePermission(String name, String subject,String permission, String authority, String  action){
        this.name = name;
        this.subject = subject;
        this.permission = permission;
        this.authority = authority;
        this.action = action;
    }


    public static final class Builder {
        private PermissionId id;
        private String name;
        private String subject;
        private String permission;
        private String authority;
        private String action;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = new PermissionId(val);
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

        public Permission build() {
            return new Permission(this);
        }
    }
}
