package com.hrmapp.user.application.dto.request;

import java.util.UUID;

public record DeactivateUserRequest(UUID userId, String deactivationReason) {
}
