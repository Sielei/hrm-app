package com.hrmapp.user.infrastructure.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, UUID> {
}