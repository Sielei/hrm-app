package com.hrmapp.user.application.dto.response;

import com.hrmapp.user.domain.entity.PasswordReset;

import java.util.Date;
import java.util.UUID;

public record CreatePasswordResetTokenResponse(UUID userId, String token, Date expiry) {
    public static Builder builder() {
        return new Builder();
    }

    public static CreatePasswordResetTokenResponse fromEntity(PasswordReset passwordReset) {
        return CreatePasswordResetTokenResponse.builder()
                .userId(passwordReset.getUserId().getValue())
                .token(passwordReset.getToken())
                .expiry(passwordReset.getExpiry())
                .build();
    }

    public static final class Builder {
        private UUID userId;
        private String token;
        private Date expiry;

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

        public Builder expiry(Date val) {
            expiry = val;
            return this;
        }

        public CreatePasswordResetTokenResponse build() {
            return new CreatePasswordResetTokenResponse(userId, token, expiry);
        }
    }
}
