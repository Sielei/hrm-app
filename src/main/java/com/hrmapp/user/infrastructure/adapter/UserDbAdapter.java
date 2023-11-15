package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.port.output.UserRepository;
import com.hrmapp.user.domain.entity.User;
import com.hrmapp.user.infrastructure.UserDataMapper;
import com.hrmapp.user.infrastructure.data.UserJpaRepository;

import java.util.Optional;

public class UserDbAdapter implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    public UserDbAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(UserDataMapper::mapUserJpaEntityToUser);
    }
}
