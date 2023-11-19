package com.hrmapp.user.domain.valueobject;

import com.hrmapp.common.domain.valueobject.BaseId;

import java.util.UUID;

public class PasswordResetId extends BaseId<UUID> {
    public PasswordResetId(UUID value) {
        super(value);
    }
}
