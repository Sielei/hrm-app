package com.hrmapp.user.application.port.input.handler.command;

import com.hrmapp.user.application.dto.command.CreatePasswordResetTokenCommand;
import com.hrmapp.user.application.dto.response.CreatePasswordResetTokenResponse;
import com.hrmapp.user.application.port.output.PasswordResetRepository;
import com.hrmapp.user.domain.UserDomainService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PasswordResetCommandHandler {
    private final PasswordResetRepository passwordResetRepository;
    private final UserDomainService userDomainService;

    public PasswordResetCommandHandler(PasswordResetRepository passwordResetRepository, UserDomainService userDomainService) {
        this.passwordResetRepository = passwordResetRepository;
        this.userDomainService = userDomainService;
    }

    public CreatePasswordResetTokenResponse handleCreatePasswordResetTokenCommand(CreatePasswordResetTokenCommand createPasswordResetTokenCommand) {
        var passwordReset = createPasswordResetTokenCommand.toEntity();
        userDomainService.createPasswordReset(passwordReset);
        var persistedResetToken = passwordResetRepository.save(passwordReset);
        return CreatePasswordResetTokenResponse.fromEntity(persistedResetToken);
    }
}
