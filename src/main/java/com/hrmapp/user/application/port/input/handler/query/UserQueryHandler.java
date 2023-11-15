package com.hrmapp.user.application.port.input.handler.query;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.application.port.output.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class UserQueryHandler {
    private final UserRepository userRepository;

    public UserQueryHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " +
                        username + " does not exist!"));
    }
}
