package com.hrmapp.user.application.dto.request;

public record UpdatePasswordPolicyRequest(String name, int passwordResetDays,
                                          int numberOfCharacters, int numberOfSpecialCharacters,
                                          int numberOfNumericCharacters, int numberOfLowercaseCharacters,
                                          int numberOfUppercaseCharacters) {
}
