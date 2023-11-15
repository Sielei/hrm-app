package com.hrmapp.user.domain.valueobject;

import com.hrmapp.common.domain.valueobject.BaseId;

import java.util.UUID;

public class PermissionId extends BaseId<UUID> {
    public PermissionId(UUID value) {
        super(value);
    }
}
