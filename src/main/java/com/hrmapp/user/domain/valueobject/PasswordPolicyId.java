package com.hrmapp.user.domain.valueobject;

import com.hrmapp.common.domain.valueobject.BaseId;

import java.util.UUID;

public class PasswordPolicyId extends BaseId<UUID> {
    public PasswordPolicyId(UUID value) {
        super(value);
    }
}
