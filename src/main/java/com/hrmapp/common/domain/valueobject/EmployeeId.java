package com.hrmapp.common.domain.valueobject;

import java.util.UUID;

public class EmployeeId extends BaseId<UUID> {
    public EmployeeId(UUID value) {
        super(value);
    }
}
