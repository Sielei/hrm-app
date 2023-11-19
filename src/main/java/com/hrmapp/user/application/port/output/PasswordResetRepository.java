package com.hrmapp.user.application.port.output;

import com.hrmapp.user.domain.entity.PasswordReset;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepository {
    PasswordReset save(PasswordReset passwordReset);

    Optional<PasswordReset> findPasswordResetToken(String token);
}
