package com.hrmapp.user.application.dto;

import java.util.UUID;

public record PermissionDto(UUID id, String name, String subject, String permission, String authority, String  action) {
}
