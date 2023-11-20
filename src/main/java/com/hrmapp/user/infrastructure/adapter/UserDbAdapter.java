package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.application.dto.response.CreateUserResponse;
import com.hrmapp.user.application.port.output.UserRepository;
import com.hrmapp.user.domain.entity.User;
import com.hrmapp.user.infrastructure.UserDataMapper;
import com.hrmapp.user.infrastructure.data.UserJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

public class UserDbAdapter implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserDataMapper userDataMapper;

    public UserDbAdapter(UserJpaRepository userJpaRepository, UserDataMapper userDataMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userDataMapper = userDataMapper;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(userDataMapper::mapUserJpaEntityToUser);
    }

    @Override
    public Optional<User> findByEmailAddress(String email) {
        return userJpaRepository.findByEmailAddress(email)
                .map(userDataMapper::mapUserJpaEntityToUser);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userJpaRepository.findById(userId)
                .map(userDataMapper::mapUserJpaEntityToUser);
    }

    @Override
    public User save(User user) {
        var userEntity = userDataMapper.mapUserToUserJpaEntity(user);
        var persistedUser = userJpaRepository.save(userEntity);
        return userDataMapper.mapUserJpaEntityToUser(persistedUser);
    }

    @Override
    public Page<CreateUserResponse> findUsers(PageRequest pageable) {
        return userJpaRepository.findAll(pageable)
                .map(userEntity -> CreateUserResponse.fromDto(UserDto.fromEntity(
                        userDataMapper.mapUserJpaEntityToUser(userEntity))));
    }
}
