package com.hrmapp.user.application.dto.command;

import com.hrmapp.user.domain.entity.PasswordReset;

import java.util.UUID;

public record CreatePasswordResetTokenCommand(UUID userId, String token) {
    public static Builder builder() {
        return new Builder();
    }

    public PasswordReset toEntity() {
        return PasswordReset.builder()
                .userId(userId)
                .token(token)
                .build();
    }

    public static final class Builder {
        private UUID userId;
        private String token;

        private Builder() {
        }

        public Builder userId(UUID val) {
            userId = val;
            return this;
        }

        public Builder token(String val) {
            token = val;
            return this;
        }

        public CreatePasswordResetTokenCommand build() {
            return new CreatePasswordResetTokenCommand(userId, token);
        }
    }
}
