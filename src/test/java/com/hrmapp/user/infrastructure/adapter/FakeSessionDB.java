package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.port.output.SessionRepository;
import com.hrmapp.user.domain.entity.Session;
import com.hrmapp.user.domain.valueobject.IPAddress;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FakeSessionDB implements SessionRepository {
    private final List<Session> sessions = new ArrayList<>();
    public FakeSessionDB(){
        var session = Session.builder()
                .id(UUID.fromString("6f292417-e96a-4951-a4f8-5b51f2cb596a"))
                .userId(UUID.fromString("03cc5361-b27c-47d9-af59-ddecd384ed93"))
                .token("ec602834-4c88-406a-88b8-048bfbef5f54")
                .ipAddress(new IPAddress("127.0.0.1"))
                .isActive(true)
                .startedAt(Instant.now())
                .expectedTerminationTime(Instant.now().plusSeconds(7200))
                .build();
        var session1 = Session.builder()
                .id(UUID.fromString("84955dba-7e67-4e6b-9a7f-923b82f96468"))
                .userId(UUID.fromString("2886e348-812a-407b-9a5a-eeac22b38b36"))
                .token("516692e2-6bd0-4894-9d3f-7789ab3f9f96")
                .ipAddress(new IPAddress("127.0.0.1"))
                .isActive(true)
                .startedAt(Instant.now())
                .expectedTerminationTime(Instant.now().plusSeconds(7200))
                .build();
        sessions.addAll(List.of(session, session1));
    }
    @Override
    public Session save(Session session) {
        var existingSession = sessions.stream()
                .filter(s -> s.getId().equals(session.getId()))
                .findFirst();
        if (existingSession.isEmpty()){
            sessions.add(session);
        }
        else {
            var index = sessions.indexOf(existingSession.get());
            sessions.set(index, session);
        }
        return session;
    }

    @Override
    public Optional<Session> findByToken(String token) {
        return sessions.stream()
                .filter(session -> session.getToken().equals(token))
                .findFirst();
    }

    @Override
    public Optional<Session> findById(UUID sessionId) {
        return sessions.stream()
                .filter(session -> session.getId().getValue().equals(sessionId))
                .findFirst();
    }
}
