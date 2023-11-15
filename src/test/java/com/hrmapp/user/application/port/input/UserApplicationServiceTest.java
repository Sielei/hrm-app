package com.hrmapp.user.application.port.input;


import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.application.port.input.handler.command.*;
import com.hrmapp.user.application.port.input.handler.query.*;
import com.hrmapp.user.infrastructure.adapter.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserApplicationServiceTest {
    private UserApplicationService userApplicationService;

    @BeforeEach
    void init(){
        var userRepository = new FakeUserDB();
        var roleRepository = new FakeRoleDB();
        var permissionRepository = new FakePermissionDB();
        var passwordPolicyRepository = new FakePasswordPolicyDB();
        var sessionRepository = new FakeSessionDB();
        var userCommandHandler = new UserCommandHandler();
        var userQueryHandler = new UserQueryHandler(userRepository);
        var roleCommandHandler = new RoleCommandHandler();
        var roleQueryHandler = new RoleQueryHandler();
        var passwordPolicyCommandHandler = new PasswordPolicyCommandHandler();
        var passwordPolicyQueryHandler = new PasswordPolicyQueryHandler();
        var permissionCommandHandler = new PermissionCommandHandler();
        var permissionQueryHandler = new PermissionQueryHandler();
        var sessionCommandHandler = new SessionCommandHandler();
        var sessionQueryHandler = new SessionQueryHandler();
        userApplicationService = new UserApplicationService(userCommandHandler, userQueryHandler,
                roleCommandHandler, roleQueryHandler, passwordPolicyCommandHandler, passwordPolicyQueryHandler,
                permissionCommandHandler, permissionQueryHandler, sessionCommandHandler, sessionQueryHandler);
    }
    @Test
    void shouldFindUserByUsername(){
        var user = userApplicationService.findUserByUsername("JUN001");
        assertThat(user).isNotNull().isExactlyInstanceOf(UserDto.class);
    }

}