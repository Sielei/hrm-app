package com.hrmapp.user.application.dto.request;

import java.util.UUID;

public record CreateUserRequest(UUID employeeId, String username, String emailAddress, UUID passwordPolicyId) {
}
