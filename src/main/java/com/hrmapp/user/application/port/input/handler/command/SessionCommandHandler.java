package com.hrmapp.user.application.port.input.handler.command;

import com.hrmapp.user.application.port.output.SessionRepository;
import com.hrmapp.user.domain.UserDomainService;
import com.hrmapp.user.domain.entity.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SessionCommandHandler {
    private final UserDomainService userDomainService;
    private final SessionRepository sessionRepository;

    public SessionCommandHandler(UserDomainService userDomainService, SessionRepository sessionRepository) {
        this.userDomainService = userDomainService;
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    public Session handleCreateSessionCommand(Session session) {
        var newSession = userDomainService.createSession(session);
        return sessionRepository.save(newSession);
    }

    @Transactional
    public Session terminateSession(Session session) {
        var terminatedSession = userDomainService.terminateSession(session);
        return sessionRepository.save(terminatedSession);
    }
}
