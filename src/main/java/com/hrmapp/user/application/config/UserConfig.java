package com.hrmapp.user.application.config;

import com.hrmapp.user.application.port.output.UserRepository;
import com.hrmapp.user.domain.UserDomainService;
import com.hrmapp.user.infrastructure.adapter.UserDbAdapter;
import com.hrmapp.user.infrastructure.data.UserJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public UserDomainService userDomainService(){
        return new UserDomainService();
    }
    @Bean
    public UserRepository userRepository(final UserJpaRepository userJpaRepository){
        return new UserDbAdapter(userJpaRepository);
    }
}
