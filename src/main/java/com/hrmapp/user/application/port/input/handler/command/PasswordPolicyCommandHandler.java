package com.hrmapp.user.application.port.input.handler.command;

import com.hrmapp.user.application.dto.request.CreatePasswordPolicyRequest;
import com.hrmapp.user.application.dto.request.CreatePasswordPolicyResponse;
import com.hrmapp.user.application.port.output.PasswordPolicyRepository;
import com.hrmapp.user.domain.UserDomainService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PasswordPolicyCommandHandler {
    private final PasswordPolicyRepository passwordPolicyRepository;
    private final UserDomainService userDomainService;

    public PasswordPolicyCommandHandler(PasswordPolicyRepository passwordPolicyRepository, UserDomainService userDomainService) {
        this.passwordPolicyRepository = passwordPolicyRepository;
        this.userDomainService = userDomainService;
    }

    public CreatePasswordPolicyResponse handleCreatePasswordPolicy(CreatePasswordPolicyRequest createPasswordPolicyRequest) {
        var passwordPolicy = createPasswordPolicyRequest.toEntity();
        userDomainService.createPasswordPolicy(passwordPolicy);
        var persistedPolicy = passwordPolicyRepository.save(passwordPolicy);
        return CreatePasswordPolicyResponse.fromEntity(persistedPolicy);
    }
}
