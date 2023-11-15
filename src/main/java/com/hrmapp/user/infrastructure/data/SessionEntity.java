package com.hrmapp.user.infrastructure.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sessions")
public class SessionEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    private UUID userId;
    @Column(length = 510)
    private String token;
    private String  ipAddress;
    private Boolean isActive;
    private Instant startedAt;
    private Instant expectedTerminationTime;
    private Instant terminatedAt;

    public SessionEntity() {
    }

    private SessionEntity(Builder builder) {
        id = builder.id;
        userId = builder.userId;
        token = builder.token;
        ipAddress = builder.ipAddress;
        isActive = builder.isActive;
        startedAt = builder.startedAt;
        expectedTerminationTime = builder.expectedTerminationTime;
        terminatedAt = builder.terminatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getExpectedTerminationTime() {
        return expectedTerminationTime;
    }

    public Instant getTerminatedAt() {
        return terminatedAt;
    }


    public static final class Builder {
        private UUID id;
        private UUID userId;
        private String token;
        private String ipAddress;
        private Boolean isActive;
        private Instant startedAt;
        private Instant expectedTerminationTime;
        private Instant terminatedAt;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder userId(UUID val) {
            userId = val;
            return this;
        }

        public Builder token(String val) {
            token = val;
            return this;
        }

        public Builder ipAddress(String val) {
            ipAddress = val;
            return this;
        }

        public Builder isActive(Boolean val) {
            isActive = val;
            return this;
        }

        public Builder startedAt(Instant val) {
            startedAt = val;
            return this;
        }

        public Builder expectedTerminationTime(Instant val) {
            expectedTerminationTime = val;
            return this;
        }

        public Builder terminatedAt(Instant val) {
            terminatedAt = val;
            return this;
        }

        public SessionEntity build() {
            return new SessionEntity(this);
        }
    }
}