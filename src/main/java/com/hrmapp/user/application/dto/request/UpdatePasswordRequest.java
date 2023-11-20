package com.hrmapp.user.application.dto.request;

public record UpdatePasswordRequest(String currentPassword, String newPassword) {
}
