package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.port.output.PasswordPolicyRepository;
import com.hrmapp.user.domain.entity.PasswordPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FakePasswordPolicyDB implements PasswordPolicyRepository {
    private final List<PasswordPolicy> passwordPolicies = new ArrayList<>();

    public FakePasswordPolicyDB(){
        var policy = PasswordPolicy.builder()
                .id(UUID.fromString("bd8cdf77-7b7c-4140-b793-a7828e32cd7c"))
                .name("Default policy")
                .passwordResetDays(90)
                .numberOfCharacters(8)
                .numberOfSpecialCharacters(0)
                .numberOfNumericCharacters(2)
                .numberOfLowercaseCharacters(3)
                .numberOfUppercaseCharacters(2)
                .build();
        var policy1 = PasswordPolicy.builder()
                .id(UUID.fromString("1252d302-e518-428b-9f40-da94fbd6cbf6"))
                .name("Administrators policy")
                .passwordResetDays(60)
                .numberOfCharacters(8)
                .numberOfSpecialCharacters(2)
                .numberOfNumericCharacters(2)
                .numberOfLowercaseCharacters(2)
                .numberOfUppercaseCharacters(2)
                .build();
        var policy2 = PasswordPolicy.builder()
                .id(UUID.fromString("1bad4ae4-0d63-4853-a8f2-c221d33c54b9"))
                .name("Managers policy")
                .passwordResetDays(30)
                .numberOfCharacters(8)
                .numberOfSpecialCharacters(2)
                .numberOfNumericCharacters(2)
                .numberOfLowercaseCharacters(2)
                .numberOfUppercaseCharacters(2)
                .build();
        passwordPolicies.addAll(List.of(policy, policy1, policy2));
    }
    @Override
    public Optional<PasswordPolicy> findById(UUID passwordPolicyId) {
        return passwordPolicies.stream().filter(policy -> policy.getId().getValue().equals(passwordPolicyId))
                .findFirst();
    }
}
