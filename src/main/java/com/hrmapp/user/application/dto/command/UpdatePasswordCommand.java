package com.hrmapp.user.application.dto.command;

import java.util.UUID;

public record UpdatePasswordCommand(UUID userId, String newPassword) {
    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private UUID userId;
        private String newPassword;

        private Builder() {
        }

        public Builder userId(UUID val) {
            userId = val;
            return this;
        }

        public Builder newPassword(String val) {
            newPassword = val;
            return this;
        }

        public UpdatePasswordCommand build() {
            return new UpdatePasswordCommand(userId, newPassword);
        }
    }
}
