package com.hrmapp.user.application.port.input.handler.query;

import com.hrmapp.user.application.port.output.PasswordResetRepository;
import com.hrmapp.user.domain.entity.PasswordReset;
import com.hrmapp.user.domain.exception.UserDomainException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class PasswordResetQueryHandler {
    private final PasswordResetRepository passwordResetRepository;

    public PasswordResetQueryHandler(PasswordResetRepository passwordResetRepository) {
        this.passwordResetRepository = passwordResetRepository;
    }

    public PasswordReset findPasswordResetByToken(String token) {
        return passwordResetRepository.findPasswordResetToken(token)
                .orElseThrow(() -> new UserDomainException(token + " is not a valid reset token!"));
    }
}
