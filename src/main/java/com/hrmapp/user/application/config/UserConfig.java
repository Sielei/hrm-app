package com.hrmapp.user.application.config;

import com.hrmapp.user.application.port.output.*;
import com.hrmapp.user.domain.UserDomainService;
import com.hrmapp.user.infrastructure.UserDataMapper;
import com.hrmapp.user.infrastructure.adapter.*;
import com.hrmapp.user.infrastructure.data.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserDomainService userDomainService(){
        return new UserDomainService();
    }
    @Bean
    public UserRepository userRepository(final UserJpaRepository userJpaRepository, final UserDataMapper userDataMapper){
        return new UserDbAdapter(userJpaRepository, userDataMapper);
    }
    @Bean
    public SessionRepository sessionRepository(final SessionJpaRepository sessionJpaRepository, final UserDataMapper userDataMapper){
        return new SessionDbAdapter(sessionJpaRepository, userDataMapper);
    }
    @Bean
    public RoleRepository roleRepository(final RoleJpaRepository roleJpaRepository, final UserDataMapper userDataMapper){
        return new RoleDbAdapter(roleJpaRepository, userDataMapper);
    }
    @Bean
    public PermissionRepository permissionRepository(final PermissionJpaRepository permissionJpaRepository){
        return new PermissionDbAdapter(permissionJpaRepository);
    }
    @Bean
    public PasswordPolicyRepository passwordPolicyRepository(final PasswordPolicyJpaRepository passwordJpaRepository,
                                                             final UserDataMapper userDataMapper){
        return new PasswordPolicyDbAdapter(passwordJpaRepository, userDataMapper);
    }
    @Bean
    public PasswordResetRepository passwordResetRepository(final PasswordResetJpaRepository passwordResetJpaRepository,
                                                           final UserDataMapper userDataMapper){
        return new PasswordResetDbAdapter(passwordResetJpaRepository, userDataMapper);
    }
}
