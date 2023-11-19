package com.hrmapp.user.application.port.input.handler.query;

import com.hrmapp.user.application.dto.PasswordPolicyDto;
import com.hrmapp.user.application.port.output.PasswordPolicyRepository;
import com.hrmapp.user.domain.exception.PasswordPolicyException;
import com.hrmapp.user.domain.exception.UserDomainException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Transactional(readOnly = true)
public class PasswordPolicyQueryHandler {
    private final PasswordPolicyRepository passwordPolicyRepository;

    public PasswordPolicyQueryHandler(PasswordPolicyRepository passwordPolicyRepository) {
        this.passwordPolicyRepository = passwordPolicyRepository;
    }

    public PasswordPolicyDto findPasswordPolicyById(UUID passwordPolicyId) {
        return passwordPolicyRepository.findById(passwordPolicyId)
                .map(PasswordPolicyDto::fromEntity)
                .orElseThrow(() -> new PasswordPolicyException("Password policy with id: " +
                        passwordPolicyId + " does not exist!"));
    }
}
