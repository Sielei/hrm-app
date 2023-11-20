package com.hrmapp.user.application.dto.request;

import com.hrmapp.user.domain.entity.PasswordPolicy;

import java.util.UUID;

public record CreatePasswordPolicyResponse(UUID id, String name, int passwordResetDays, int numberOfCharacters,
                                           int numberOfSpecialCharacters, int numberOfNumericCharacters,
                                           int numberOfLowercaseCharacters, int numberOfUppercaseCharacters) {
    public static Builder builder() {
        return new Builder();
    }

    public static CreatePasswordPolicyResponse fromEntity(PasswordPolicy passwordPolicy) {
        return CreatePasswordPolicyResponse.builder()
                .id(passwordPolicy.getId().getValue())
                .name(passwordPolicy.getName())
                .passwordResetDays(builder().passwordResetDays)
                .numberOfCharacters(passwordPolicy.getNumberOfCharacters())
                .numberOfSpecialCharacters(passwordPolicy.getNumberOfSpecialCharacters())
                .numberOfLowercaseCharacters(passwordPolicy.getNumberOfLowercaseCharacters())
                .numberOfNumericCharacters(passwordPolicy.getNumberOfNumericCharacters())
                .numberOfUppercaseCharacters(passwordPolicy.getNumberOfUppercaseCharacters())
                .build();
    }

    public static final class Builder {
        private UUID id;
        private String name;
        private int passwordResetDays;
        private int numberOfCharacters;
        private int numberOfSpecialCharacters;
        private int numberOfNumericCharacters;
        private int numberOfLowercaseCharacters;
        private int numberOfUppercaseCharacters;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder passwordResetDays(int val) {
            passwordResetDays = val;
            return this;
        }

        public Builder numberOfCharacters(int val) {
            numberOfCharacters = val;
            return this;
        }

        public Builder numberOfSpecialCharacters(int val) {
            numberOfSpecialCharacters = val;
            return this;
        }

        public Builder numberOfNumericCharacters(int val) {
            numberOfNumericCharacters = val;
            return this;
        }

        public Builder numberOfLowercaseCharacters(int val) {
            numberOfLowercaseCharacters = val;
            return this;
        }

        public Builder numberOfUppercaseCharacters(int val) {
            numberOfUppercaseCharacters = val;
            return this;
        }

        public CreatePasswordPolicyResponse build() {
            return new CreatePasswordPolicyResponse(id, name, passwordResetDays, numberOfCharacters, numberOfSpecialCharacters,
                    numberOfNumericCharacters, numberOfLowercaseCharacters, numberOfUppercaseCharacters);
        }
    }
}
