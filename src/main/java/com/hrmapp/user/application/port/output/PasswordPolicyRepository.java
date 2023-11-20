package com.hrmapp.user.application.port.output;

import com.hrmapp.user.domain.entity.PasswordPolicy;

import java.util.Optional;
import java.util.UUID;

public interface PasswordPolicyRepository {
    Optional<PasswordPolicy> findById(UUID passwordPolicyId);

    PasswordPolicy save(PasswordPolicy passwordPolicy);
}
