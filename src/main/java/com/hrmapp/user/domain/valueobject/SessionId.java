package com.hrmapp.user.domain.valueobject;

import com.hrmapp.common.domain.valueobject.BaseId;

import java.util.UUID;

public class SessionId extends BaseId<UUID> {
    public SessionId(UUID value) {
        super(value);
    }
}
