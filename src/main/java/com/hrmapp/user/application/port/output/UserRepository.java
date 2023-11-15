package com.hrmapp.user.application.port.output;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.domain.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
}
