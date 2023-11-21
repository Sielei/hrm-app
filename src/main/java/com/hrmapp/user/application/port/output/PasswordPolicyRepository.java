package com.hrmapp.user.application.port.output;

import com.hrmapp.user.application.dto.PasswordPolicyDto;
import com.hrmapp.user.domain.entity.PasswordPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

public interface PasswordPolicyRepository {
    Optional<PasswordPolicy> findById(UUID passwordPolicyId);

    PasswordPolicy save(PasswordPolicy passwordPolicy);

    Page<PasswordPolicyDto> findPasswordPolicies(PageRequest pageable);

    void delete(PasswordPolicy passwordPolicy);
}
