package com.hrmapp.user.infrastructure.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SessionJpaRepository extends JpaRepository<SessionEntity, UUID> {
    Optional<SessionEntity> findByToken(String token);
}