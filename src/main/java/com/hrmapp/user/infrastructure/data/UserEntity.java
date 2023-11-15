package com.hrmapp.user.infrastructure.data;

import com.hrmapp.user.domain.valueobject.UserStatus;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    private UUID employeeId;
    private String username;
    private String emailAddress;
    private String password;
    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private UUID createdBy;
    private Boolean changePasswordNextLogin;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "password_policy", nullable = false)
    private PasswordPolicyEntity passwordPolicy;

    public UserEntity() {
    }

    private UserEntity(Builder builder) {
        id = builder.id;
        employeeId = builder.employeeId;
        username = builder.username;
        emailAddress = builder.emailAddress;
        password = builder.password;
        roles = builder.roles;
        status = builder.status;
        createdBy = builder.createdBy;
        changePasswordNextLogin = builder.changePasswordNextLogin;
        passwordPolicy = builder.passwordPolicy;
    }
    public static Builder builder() {
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public Boolean getChangePasswordNextLogin() {
        return changePasswordNextLogin;
    }

    public PasswordPolicyEntity getPasswordPolicy() {
        return passwordPolicy;
    }


    public static final class Builder {
        private UUID id;
        private UUID employeeId;
        private String username;
        private String emailAddress;
        private String password;
        private Set<RoleEntity> roles;
        private UserStatus status;
        private UUID createdBy;
        private Boolean changePasswordNextLogin;
        private PasswordPolicyEntity passwordPolicy;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder employeeId(UUID val) {
            employeeId = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder emailAddress(String val) {
            emailAddress = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder roles(Set<RoleEntity> val) {
            roles = val;
            return this;
        }

        public Builder status(UserStatus val) {
            status = val;
            return this;
        }

        public Builder createdBy(UUID val) {
            createdBy = val;
            return this;
        }

        public Builder changePasswordNextLogin(Boolean val) {
            changePasswordNextLogin = val;
            return this;
        }

        public Builder passwordPolicy(PasswordPolicyEntity val) {
            passwordPolicy = val;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this);
        }
    }
}