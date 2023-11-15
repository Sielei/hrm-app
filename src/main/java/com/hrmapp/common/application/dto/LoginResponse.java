package com.hrmapp.common.application.dto;

public record LoginResponse(String token) {
    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private String token;

        private Builder() {
        }

        public Builder token(String val) {
            token = val;
            return this;
        }

        public LoginResponse build() {
            return new LoginResponse(token);
        }
    }
}
