package com.hrmapp.user.application.dto.request;

import com.hrmapp.user.domain.entity.PasswordPolicy;

public record CreatePasswordPolicyRequest(String name, int passwordResetDays, int numberOfCharacters,
         int numberOfSpecialCharacters, int numberOfNumericCharacters, int numberOfLowercaseCharacters,
         int numberOfUppercaseCharacters) {
    public PasswordPolicy toEntity() {
        return PasswordPolicy.builder()
                .name(name)
                .passwordResetDays(passwordResetDays)
                .numberOfCharacters(numberOfCharacters)
                .numberOfSpecialCharacters(numberOfSpecialCharacters)
                .numberOfNumericCharacters(numberOfNumericCharacters)
                .numberOfLowercaseCharacters(numberOfLowercaseCharacters)
                .numberOfUppercaseCharacters(numberOfUppercaseCharacters)
                .build();
    }
}
