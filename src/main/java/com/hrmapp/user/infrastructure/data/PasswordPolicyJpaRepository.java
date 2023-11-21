package com.hrmapp.user.infrastructure.data;

import com.hrmapp.user.application.dto.PasswordPolicyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PasswordPolicyJpaRepository extends JpaRepository<PasswordPolicyEntity, UUID> {
    @Query("""
            select new com.hrmapp.user.application.dto.PasswordPolicyDto(p.id, p.name, p.passwordResetDays,
            p.numberOfCharacters, p.numberOfSpecialCharacters, p.numberOfNumericCharacters, p.numberOfLowercaseCharacters,
            p.numberOfUppercaseCharacters)
            from PasswordPolicyEntity p
           """)
    Page<PasswordPolicyDto> findPasswordPolicies(Pageable pageable);
}