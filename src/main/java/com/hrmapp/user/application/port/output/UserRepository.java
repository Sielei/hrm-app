package com.hrmapp.user.application.port.output;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.application.dto.response.CreateUserResponse;
import com.hrmapp.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmailAddress(String email);

    Optional<User> findById(UUID userId);

    User save(User user);

    Page<CreateUserResponse> findUsers(PageRequest pageable);
}
