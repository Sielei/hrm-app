package com.hrmapp.user.domain.valueobject;

import com.hrmapp.common.domain.valueobject.BaseId;

import java.util.UUID;

public class RoleId extends BaseId<UUID> {
    public RoleId(UUID value) {
        super(value);
    }
}
