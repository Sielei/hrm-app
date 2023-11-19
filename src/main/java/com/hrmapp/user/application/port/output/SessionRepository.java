package com.hrmapp.user.application.port.output;

import com.hrmapp.user.domain.entity.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository {
    Session save(Session newSession);

    Optional<Session> findByToken(String token);

    Optional<Session> findById(UUID sessionId);
}
