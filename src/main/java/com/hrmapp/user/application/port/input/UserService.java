package com.hrmapp.user.application.port.input;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.common.application.port.input.util.PasswordUtil;
import com.hrmapp.user.application.dto.command.CreateUserCommand;
import com.hrmapp.user.application.dto.command.UpdatePasswordCommand;
import com.hrmapp.user.application.dto.request.CreateUserRequest;
import com.hrmapp.user.application.dto.request.UpdatePasswordRequest;
import com.hrmapp.user.domain.exception.UserDomainException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final PasswordUtil passwordUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(PasswordUtil passwordUtil, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passwordUtil = passwordUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public CreateUserCommand generateCreateUserCommand(CreateUserRequest createUserRequest, UUID createdBy) {
        var generatedPassword = passwordUtil.generateNewUserPassword(createUserRequest.passwordPolicyId());
        var password = bCryptPasswordEncoder.encode(generatedPassword);
        return CreateUserCommand.builder()
                .employeeId(createUserRequest.employeeId())
                .username(createUserRequest.username())
                .emailAddress(createUserRequest.emailAddress())
                .password(password)
                .genPassword(generatedPassword)
                .passwordPolicyId(createUserRequest.passwordPolicyId())
                .createdBy(createdBy)
                .changePasswordNextLogin(true)
                .build();
    }

    public UpdatePasswordCommand generateUpdatePasswordCommand(UserDto userDto, UpdatePasswordRequest updatePasswordRequest) {
        if (!bCryptPasswordEncoder.matches(updatePasswordRequest.currentPassword(), userDto.password())){
            throw new UserDomainException("Current password does not match with the password you provided!");
        }
        passwordUtil.validatePassword(userDto, updatePasswordRequest.newPassword());
        var newPassword = bCryptPasswordEncoder.encode(updatePasswordRequest.newPassword());
        return UpdatePasswordCommand.builder()
                .userId(userDto.id())
                .newPassword(newPassword)
                .build();
    }
}
