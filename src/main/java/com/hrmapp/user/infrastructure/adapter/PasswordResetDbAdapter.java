package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.port.output.PasswordResetRepository;
import com.hrmapp.user.domain.entity.PasswordReset;
import com.hrmapp.user.infrastructure.UserDataMapper;
import com.hrmapp.user.infrastructure.data.PasswordResetJpaRepository;
import org.apache.logging.slf4j.SLF4JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PasswordResetDbAdapter implements PasswordResetRepository {
    private final PasswordResetJpaRepository passwordResetJpaRepository;
    private final UserDataMapper userDataMapper;
    private static final Logger log = LoggerFactory.getLogger(SLF4JLogger.class);

    public PasswordResetDbAdapter(PasswordResetJpaRepository passwordResetJpaRepository, UserDataMapper userDataMapper) {
        this.passwordResetJpaRepository = passwordResetJpaRepository;
        this.userDataMapper = userDataMapper;
    }

    @Override
    public PasswordReset save(PasswordReset passwordReset) {
        var passwordResetEntity = userDataMapper.mapPasswordResetToPasswordResetJpaEntity(passwordReset);
        var persistedEntity = passwordResetJpaRepository.save(passwordResetEntity);
        log.info("New password reset request for {} has been created!", persistedEntity.getUser().getUsername());
        return userDataMapper.mapPasswordResetJpaEntityToPasswordReset(persistedEntity);
    }

    @Override
    public Optional<PasswordReset> findPasswordResetToken(String token) {
        return passwordResetJpaRepository.findByToken(token)
                .map(userDataMapper::mapPasswordResetJpaEntityToPasswordReset);
    }
}
