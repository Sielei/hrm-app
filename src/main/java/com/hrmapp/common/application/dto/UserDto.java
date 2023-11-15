package com.hrmapp.common.application.dto;

import com.hrmapp.user.domain.entity.Role;
import com.hrmapp.user.domain.entity.User;
import com.hrmapp.user.domain.valueobject.UserStatus;

import java.util.Set;
import java.util.UUID;

public record UserDto(UUID id, String username, String password, UserStatus userStatus,
                      Set<Role> roles) {
    public static Builder builder() {
        return new Builder();
    }

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId().getValue())
                .username(user.getUsername())
                .password(user.getPassword())
                .userStatus(user.getStatus())
                .roles(user.getRoles())
                .build();
    }

    public static final class Builder {
        private UUID id;
        private String username;
        private String password;
        private UserStatus userStatus;
        private Set<Role> roles;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
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

        public UserDto build() {
            return new UserDto(id, username, password, userStatus, roles);
        }
    }
}
