package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.port.output.SessionRepository;
import com.hrmapp.user.domain.entity.Session;
import com.hrmapp.user.infrastructure.UserDataMapper;
import com.hrmapp.user.infrastructure.data.SessionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SessionDbAdapter implements SessionRepository {
    private final SessionJpaRepository sessionJpaRepository;
    private final UserDataMapper userDataMapper;

    public SessionDbAdapter(SessionJpaRepository sessionJpaRepository, UserDataMapper userDataMapper) {
        this.sessionJpaRepository = sessionJpaRepository;
        this.userDataMapper = userDataMapper;
    }

    @Override
    public Session save(Session session) {
        var sessionEntity = userDataMapper.mapSessionToSessionJpaEntity(session);
        var persistedSession = sessionJpaRepository.save(sessionEntity);
        return userDataMapper.mapSessionJpaEntityToSession(persistedSession);
    }

    @Override
    public Optional<Session> findByToken(String token) {
        return sessionJpaRepository.findByToken(token)
                .map(userDataMapper::mapSessionJpaEntityToSession);
    }

    @Override
    public Optional<Session> findById(UUID sessionId) {
        return sessionJpaRepository.findById(sessionId)
                .map(userDataMapper::mapSessionJpaEntityToSession);
    }
}
