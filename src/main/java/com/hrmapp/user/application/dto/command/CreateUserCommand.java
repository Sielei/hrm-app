package com.hrmapp.user.application.dto.command;

import com.hrmapp.user.domain.entity.User;

import java.util.UUID;

public record CreateUserCommand(UUID employeeId, String username, String emailAddress, String password,
                                UUID passwordPolicyId, UUID createdBy, boolean changePasswordNextLogin) {
    public static Builder builder() {
        return new Builder();
    }

    public User toUser() {
        return User.builder()
                .employeeId(employeeId)
                .username(username)
                .emailAddress(emailAddress)
                .password(password)
                .passwordPolicyId(passwordPolicyId)
                .createdBy(createdBy)
                .changePasswordNextLogin(changePasswordNextLogin)
                .build();
    }


    public static final class Builder {
        private UUID employeeId;
        private String username;
        private String emailAddress;
        private String password;
        private UUID passwordPolicyId;
        private UUID createdBy;
        private boolean changePasswordNextLogin;

        private Builder() {
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

        public Builder passwordPolicyId(UUID val) {
            passwordPolicyId = val;
            return this;
        }

        public Builder createdBy(UUID val) {
            createdBy = val;
            return this;
        }

        public Builder changePasswordNextLogin(boolean val) {
            changePasswordNextLogin = val;
            return this;
        }

        public CreateUserCommand build() {
            return new CreateUserCommand(employeeId, username, emailAddress, password, passwordPolicyId, createdBy,
                    changePasswordNextLogin);
        }
    }
}
