package com.hrmapp.common.application.dto.request;

public record ResetPasswordRequest(String token, String newPassword) {
    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private String token;
        private String newPassword;

        private Builder() {
        }

        public Builder token(String val) {
            token = val;
            return this;
        }

        public Builder newPassword(String val) {
            newPassword = val;
            return this;
        }

        public ResetPasswordRequest build() {
            return new ResetPasswordRequest(token, newPassword);
        }
    }
}
