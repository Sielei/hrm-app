package com.hrmapp.user.application.port.output;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmailAddress(String email);

    Optional<User> findById(UUID userId);

    User save(User user);
}
