package com.hrmapp.user.application.port.input.handler.query;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.application.port.output.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

    public UserDto findByEmailAddress(String email) {
        return userRepository.findByEmailAddress(email)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " +
                        email + " does not exist!"));
    }

    public UserDto findById(UUID userId) {
        return userRepository.findById(userId)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("User with id: " +
                        userId + " does not exist!"));
    }
}
