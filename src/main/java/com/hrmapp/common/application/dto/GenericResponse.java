package com.hrmapp.common.application.dto;

public record GenericResponse(Boolean success, String message) {
    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private Boolean success;
        private String message;

        private Builder() {
        }

        public Builder success(Boolean val) {
            success = val;
            return this;
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public GenericResponse build() {
            return new GenericResponse(success, message);
        }
    }
}
