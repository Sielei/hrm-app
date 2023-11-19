package com.hrmapp.user.application.dto;

import com.hrmapp.user.domain.entity.PasswordPolicy;

import java.util.UUID;

public record PasswordPolicyDto(UUID passwordPolicyId, String name, int passwordResetDays,
                                int numberOfCharacters, int numberOfSpecialCharacters,
                                int numberOfNumericCharacters, int numberOfLowercaseCharacters,
                                int numberOfUppercaseCharacters) {
    public static Builder builder() {
        return new Builder();
    }

    public static PasswordPolicyDto fromEntity(PasswordPolicy passwordPolicy) {
        return PasswordPolicyDto.builder()
                .passwordPolicyId(passwordPolicy.getId().getValue())
                .name(passwordPolicy.getName())
                .passwordResetDays(passwordPolicy.getPasswordResetDays())
                .numberOfCharacters(passwordPolicy.getNumberOfCharacters())
                .numberOfSpecialCharacters(passwordPolicy.getNumberOfSpecialCharacters())
                .numberOfNumericCharacters(passwordPolicy.getNumberOfNumericCharacters())
                .numberOfLowercaseCharacters(passwordPolicy.getNumberOfLowercaseCharacters())
                .numberOfUppercaseCharacters(passwordPolicy.getNumberOfUppercaseCharacters())
                .build();
    }

    public static final class Builder {
        private UUID passwordPolicyId;
        private String name;
        private int passwordResetDays;
        private int numberOfCharacters;
        private int numberOfSpecialCharacters;
        private int numberOfNumericCharacters;
        private int numberOfLowercaseCharacters;
        private int numberOfUppercaseCharacters;

        private Builder() {
        }

        public Builder passwordPolicyId(UUID val) {
            passwordPolicyId = val;
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

        public PasswordPolicyDto build() {
            return new PasswordPolicyDto(passwordPolicyId, name, passwordResetDays,
                    numberOfCharacters, numberOfSpecialCharacters, numberOfNumericCharacters,
                    numberOfLowercaseCharacters, numberOfUppercaseCharacters);
        }
    }
}
