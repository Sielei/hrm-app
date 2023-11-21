package com.hrmapp.user.application.port.input.handler.command;

import com.hrmapp.user.application.dto.PasswordPolicyDto;
import com.hrmapp.user.application.dto.request.CreatePasswordPolicyRequest;
import com.hrmapp.user.application.dto.request.CreatePasswordPolicyResponse;
import com.hrmapp.user.application.dto.request.UpdatePasswordPolicyRequest;
import com.hrmapp.user.application.port.output.PasswordPolicyRepository;
import com.hrmapp.user.domain.UserDomainService;
import com.hrmapp.user.domain.entity.PasswordPolicy;
import com.hrmapp.user.domain.exception.PasswordPolicyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

    public PasswordPolicyDto handleUpdatePasswordPolicyRequest(UUID passwordPolicyId, UpdatePasswordPolicyRequest updatePasswordPolicyRequest) {
        var passwordPolicy = findPasswordPolicyById(passwordPolicyId);
        userDomainService.updatePasswordPolicy(passwordPolicy, updatePasswordPolicyRequest.name(),
                updatePasswordPolicyRequest.passwordResetDays(), updatePasswordPolicyRequest.numberOfCharacters(),
                updatePasswordPolicyRequest.numberOfSpecialCharacters(), updatePasswordPolicyRequest.numberOfNumericCharacters(),
                updatePasswordPolicyRequest.numberOfLowercaseCharacters(), updatePasswordPolicyRequest.numberOfUppercaseCharacters());
        var updatedPasswordPolicy = passwordPolicyRepository.save(passwordPolicy);
        return PasswordPolicyDto.fromEntity(updatedPasswordPolicy);
    }

    private PasswordPolicy findPasswordPolicyById(UUID passwordPolicyId) {
        return passwordPolicyRepository.findById(passwordPolicyId)
                .orElseThrow(()-> new PasswordPolicyException("Password policy with id: " + passwordPolicyId
                + " does not exist!"));
    }

    public void handleDeletePasswordPolicy(UUID passwordPolicyId) {
        var passwordPolicy = findPasswordPolicyById(passwordPolicyId);
        passwordPolicyRepository.delete(passwordPolicy);
    }
}
