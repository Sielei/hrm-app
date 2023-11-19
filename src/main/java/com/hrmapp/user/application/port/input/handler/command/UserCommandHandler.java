package com.hrmapp.user.application.port.input.handler.command;

import com.hrmapp.user.application.dto.command.UpdatePasswordCommand;
import com.hrmapp.user.application.port.output.UserRepository;
import com.hrmapp.user.domain.UserDomainService;
import com.hrmapp.user.domain.exception.UserDomainException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserCommandHandler {
    private final UserRepository userRepository;
    private final UserDomainService userDomainService;

    public UserCommandHandler(UserRepository userRepository, UserDomainService userDomainService) {
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
    }

    public void handleUpdatePasswordCommand(UpdatePasswordCommand updatePasswordCommand) {
        var user = userRepository.findById(updatePasswordCommand.userId())
                .orElseThrow(() -> new UserDomainException("User with id:" +
                        updatePasswordCommand.userId() + " not found!"));
        userDomainService.updateUserPassword(user, updatePasswordCommand.newPassword());
        var updatedUser = userRepository.save(user);
    }
}
