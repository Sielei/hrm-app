package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.dto.PasswordPolicyDto;
import com.hrmapp.user.application.port.output.PasswordPolicyRepository;
import com.hrmapp.user.domain.entity.PasswordPolicy;
import com.hrmapp.user.infrastructure.UserDataMapper;
import com.hrmapp.user.infrastructure.data.PasswordPolicyJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PasswordPolicyDbAdapter implements PasswordPolicyRepository {
    private final PasswordPolicyJpaRepository passwordPolicyJpaRepository;
    private final UserDataMapper userDataMapper;

    public PasswordPolicyDbAdapter(PasswordPolicyJpaRepository passwordPolicyJpaRepository, UserDataMapper userDataMapper) {
        this.passwordPolicyJpaRepository = passwordPolicyJpaRepository;
        this.userDataMapper = userDataMapper;
    }

    @Override
    public Optional<PasswordPolicy> findById(UUID passwordPolicyId) {
        return passwordPolicyJpaRepository.findById(passwordPolicyId)
                .map(userDataMapper::mapPasswordPolicyJpaEntityToPasswordPolicy);
    }

    @Override
    public PasswordPolicy save(PasswordPolicy passwordPolicy) {
        var passwordPolicyEntity = userDataMapper.mapPasswordPolicyToPasswordPolicyJpaEntity(passwordPolicy);
        var newPasswordPolicy = passwordPolicyJpaRepository.save(passwordPolicyEntity);
        return userDataMapper.mapPasswordPolicyJpaEntityToPasswordPolicy(newPasswordPolicy);
    }

    @Override
    public Page<PasswordPolicyDto> findPasswordPolicies(PageRequest pageable) {
        return passwordPolicyJpaRepository.findPasswordPolicies(pageable);
    }

    @Override
    public void delete(PasswordPolicy passwordPolicy) {
        var passwordPolicyEntity = userDataMapper.mapPasswordPolicyToPasswordPolicyJpaEntity(passwordPolicy);
        passwordPolicyJpaRepository.delete(passwordPolicyEntity);
    }
}
