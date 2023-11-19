package com.hrmapp.user.application.port.input;


import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.application.dto.command.CreatePasswordResetTokenCommand;
import com.hrmapp.user.application.dto.command.UpdatePasswordCommand;
import com.hrmapp.user.application.dto.response.CreatePasswordResetTokenResponse;
import com.hrmapp.user.application.port.input.handler.command.*;
import com.hrmapp.user.application.port.input.handler.query.*;
import com.hrmapp.user.domain.UserDomainService;
import com.hrmapp.user.domain.entity.Session;
import com.hrmapp.user.domain.valueobject.IPAddress;
import com.hrmapp.user.domain.valueobject.SessionId;
import com.hrmapp.user.infrastructure.adapter.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class UserApplicationServiceTest {
    private UserApplicationService userApplicationService;

    @BeforeEach
    void init(){
        var userRepository = new FakeUserDB();
        var passwordResetRepository = new FakePasswordResetDB();
        var roleRepository = new FakeRoleDB();
        var permissionRepository = new FakePermissionDB();
        var passwordPolicyRepository = new FakePasswordPolicyDB();
        var sessionRepository = new FakeSessionDB();
        var userDomainService = new UserDomainService();
        var userCommandHandler = new UserCommandHandler(userRepository, userDomainService);
        var userQueryHandler = new UserQueryHandler(userRepository);
        var roleCommandHandler = new RoleCommandHandler();
        var roleQueryHandler = new RoleQueryHandler();
        var passwordResetCommandHandler = new PasswordResetCommandHandler(passwordResetRepository, userDomainService);
        var passwordResetQueryHandler = new PasswordResetQueryHandler(passwordResetRepository);
        var passwordPolicyCommandHandler = new PasswordPolicyCommandHandler();
        var passwordPolicyQueryHandler = new PasswordPolicyQueryHandler(passwordPolicyRepository);
        var permissionCommandHandler = new PermissionCommandHandler();
        var permissionQueryHandler = new PermissionQueryHandler();
        var sessionCommandHandler = new SessionCommandHandler(userDomainService, sessionRepository);
        var sessionQueryHandler = new SessionQueryHandler(sessionRepository);
        userApplicationService = new UserApplicationService(userCommandHandler, userQueryHandler,
                passwordResetCommandHandler, passwordResetQueryHandler, roleCommandHandler, roleQueryHandler, passwordPolicyCommandHandler, passwordPolicyQueryHandler,
                permissionCommandHandler, permissionQueryHandler, sessionCommandHandler, sessionQueryHandler);
    }
    @Test
    void shouldFindUserById(){
        var user = userApplicationService.findUserById(UUID.fromString("03cc5361-b27c-47d9-af59-ddecd384ed93"));
        assertThat(user).isNotNull().isExactlyInstanceOf(UserDto.class);
    }
    @Test
    void shouldThrowUserNotFoundExceptionIfUserIdDoesNotExist(){
        var userId = UUID.fromString("734b48c1-3e38-4bd1-bcb2-1677f930a84d");
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userApplicationService.findUserById(userId))
                .withMessage("User with id: "+ userId + " does not exist!");
    }
    @Test
    void shouldFindUserByUsernameIfUserExists(){
        var user = userApplicationService.findUserByUsername("JUN001");
        assertThat(user).isNotNull().isExactlyInstanceOf(UserDto.class);
    }
    @Test
    void shouldThrowUserNotFoundExceptionIfUsernameDoesNotExist(){
        var username = "UNKNOWN";
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userApplicationService.findUserByUsername(username))
                .withMessage("User with username: "+ username + " does not exist!");
    }
    @Test
    void shouldFindUserByEmailAddressIfUserExists(){
        var user = userApplicationService.findUserByEmailAddress("tjun@gmail.com");
        assertThat(user).isNotNull().isExactlyInstanceOf(UserDto.class);
    }
    @Test
    void shouldThrowUserNotFoundExceptionIfEmailDoesNotExist(){
        var email = "unknown@gmail.com";
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userApplicationService.findUserByEmailAddress(email))
                .withMessage("User with email: "+ email + " does not exist!");
    }
    @Test
    void shouldCreatePasswordResetToken(){
        var createPasswordResetTokenCommand = CreatePasswordResetTokenCommand.builder()
                .userId(UUID.fromString("03cc5361-b27c-47d9-af59-ddecd384ed93"))
                .token(UUID.randomUUID().toString())
                .build();
        assertThat(userApplicationService.handleCreatePasswordResetTokenCommand(createPasswordResetTokenCommand))
                .isNotNull().isExactlyInstanceOf(CreatePasswordResetTokenResponse.class);
    }

    @Test
    void shouldFindUserByResetToken(){
        var token = UUID.fromString("de8e4d56-46e4-4610-ab9b-bef4f86ad945").toString();
        var user = userApplicationService.findUserByResetToken(token);
        assertThat(user.username()).isNotNull().isNotBlank().isEqualTo("JUN001");
    }

    @Test
    void shouldFindPasswordPolicyById(){
        var policyId = UUID.fromString("bd8cdf77-7b7c-4140-b793-a7828e32cd7c");
        var policy = userApplicationService.findPasswordPolicyById(policyId);
        assertThat(policy.name()).isNotNull().isNotBlank().isEqualTo("Default policy");
    }
    @Test
    void shouldUpdateUserPassword(){
        var updatePasswordCommand = UpdatePasswordCommand.builder()
                .userId(UUID.fromString("2886e348-812a-407b-9a5a-eeac22b38b36"))
                .newPassword("P@5sW0rd")
                .build();
        assertThatNoException().isThrownBy(() -> userApplicationService.handleUpdatePasswordCommand(updatePasswordCommand));
    }
    @Test
    void shouldCreateSession(){
        var session = Session.builder()
                .userId(UUID.fromString("320c9635-c767-411b-af91-73685ea77490"))
                .token("2c09cfca-575b-44eb-a001-6e6d161d7f7c")
                .ipAddress(new IPAddress("127.0.0.1"))
                .isActive(true)
                .startedAt(Instant.now())
                .expectedTerminationTime(Instant.now().plusSeconds(7200))
                .build();
        var newSession = userApplicationService.createSession(session);
        assertThat(newSession.getId())
                .isNotNull().isExactlyInstanceOf(SessionId.class);
    }
    @Test
    void shouldFindSessionByToken(){
        var session = userApplicationService.findSessionByToken("ec602834-4c88-406a-88b8-048bfbef5f54");
        assertThat(session.getId()).isNotNull()
                .isEqualTo(new SessionId(UUID.fromString("6f292417-e96a-4951-a4f8-5b51f2cb596a")));
    }
    @Test
    void shouldTerminateSession(){
        var sessionId = UUID.fromString("6f292417-e96a-4951-a4f8-5b51f2cb596a");
        var session = userApplicationService.terminateSession(sessionId);
        assertThat(session.getActive()).isFalse();
    }
    @Test
    void shouldDeactivateActiveUser(){
    }
    @Test
    void shouldActivateUser(){}

}