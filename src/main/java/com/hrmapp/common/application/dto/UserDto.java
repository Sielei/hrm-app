package com.hrmapp.common.application.dto;

import com.hrmapp.user.domain.entity.Role;
import com.hrmapp.user.domain.entity.User;
import com.hrmapp.user.domain.valueobject.UserStatus;

import java.util.Set;
import java.util.UUID;

public record UserDto(UUID id, UUID employeeId, String username, String emailAddress, String password, UserStatus userStatus,
                      Set<Role> roles, UUID passwordPolicyId) {
    public static Builder builder() {
        return new Builder();
    }

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId().getValue())
                .employeeId(user.getEmployeeId().getValue())
                .username(user.getUsername())
                .emailAddress(user.getEmailAddress())
                .password(user.getPassword())
                .userStatus(user.getStatus())
                .roles(user.getRoles())
                .passwordPolicyId(user.getPasswordPolicyId().getValue())
                .build();
    }

    public static final class Builder {
        private UUID id;
        private UUID employeeId;
        private String username;
        private String emailAddress;
        private String password;
        private UserStatus userStatus;
        private Set<Role> roles;
        private UUID passwordPolicyId;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder employeeId(UUID val){
            employeeId = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder emailAddress(String val){
            emailAddress = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder userStatus(UserStatus val) {
            userStatus = val;
            return this;
        }

        public Builder roles(Set<Role> val) {
            roles = val;
            return this;
        }
        public Builder passwordPolicyId(UUID val){
            passwordPolicyId = val;
            return this;
        }

        public UserDto build() {
            return new UserDto(id, employeeId, username, emailAddress, password, userStatus, roles, passwordPolicyId);
        }
    }
}
