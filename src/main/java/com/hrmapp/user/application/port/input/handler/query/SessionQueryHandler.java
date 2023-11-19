package com.hrmapp.user.application.port.input.handler.query;

import com.hrmapp.user.application.port.output.SessionRepository;
import com.hrmapp.user.domain.entity.Session;
import com.hrmapp.user.domain.exception.UserDomainException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Transactional
public class SessionQueryHandler {
    private final SessionRepository sessionRepository;

    public SessionQueryHandler(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session findSessionByToken(String token) {
        return sessionRepository.findByToken(token)
                .orElseThrow(() -> new UserDomainException("No session exists for the token!"));
    }

    public Session findSessionById(UUID sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new UserDomainException("Session with id: " + sessionId +" does not exist!"));
    }
}
