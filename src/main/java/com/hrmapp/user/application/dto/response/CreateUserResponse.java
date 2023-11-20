package com.hrmapp.user.application.dto.response;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.domain.entity.Role;
import com.hrmapp.user.domain.valueobject.UserStatus;

import java.util.Set;
import java.util.UUID;

public record CreateUserResponse(UUID id, UUID employeeId, String username, String emailAddress, UserStatus userStatus,
                                 Set<Role> roles, UUID passwordPolicyId) {
    public static Builder builder() {
        return new Builder();
    }
    public static CreateUserResponse fromDto(UserDto userDto) {
        return CreateUserResponse.builder()
                .id(userDto.id())
                .employeeId(userDto.employeeId())
                .username(userDto.username())
                .emailAddress(userDto.emailAddress())
                .userStatus(userDto.userStatus())
                .roles(userDto.roles())
                .passwordPolicyId(userDto.passwordPolicyId())
                .build();
    }

    public static final class Builder {
        private UUID id;
        private UUID employeeId;
        private String username;
        private String emailAddress;
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
            return  this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder emailAddress(String val) {
            emailAddress = val;
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

        public Builder passwordPolicyId(UUID val) {
            passwordPolicyId = val;
            return this;
        }

        public CreateUserResponse build() {
            return new CreateUserResponse(id, employeeId, username, emailAddress, userStatus, roles, passwordPolicyId);
        }
    }
}
