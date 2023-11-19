package com.hrmapp.user.infrastructure.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetJpaRepository extends JpaRepository<PasswordResetEntity, UUID> {
    Optional<PasswordResetEntity> findByToken(String token);
}