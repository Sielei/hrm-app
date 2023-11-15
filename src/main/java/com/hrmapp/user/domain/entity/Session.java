package com.hrmapp.user.domain.entity;

import com.hrmapp.common.domain.entity.BaseEntity;
import com.hrmapp.common.domain.valueobject.UserId;
import com.hrmapp.user.domain.valueobject.IPAddress;
import com.hrmapp.user.domain.valueobject.SessionId;

import java.time.Instant;
import java.util.UUID;

public class Session extends BaseEntity<SessionId> {
    private final UserId userId;
    private final String token;
    private final IPAddress ipAddress;
    private Boolean isActive;
    private final Instant startedAt;
    private final Instant expectedTerminationTime;
    private Instant terminatedAt;

    private Session(Builder builder) {
        super.setId(builder.id);
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

    public UserId getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public IPAddress getIpAddress() {
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

    public void createSession(){
        setId(new SessionId(UUID.randomUUID()));
    }

    public void terminateSession(){
        this.isActive = false;
        this.terminatedAt = Instant.now();
    }


    public static final class Builder {
        private SessionId id;
        private UserId userId;
        private String token;
        private IPAddress ipAddress;
        private Boolean isActive;
        private Instant startedAt;
        private Instant expectedTerminationTime;
        private Instant terminatedAt;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = new SessionId(val);
            return this;
        }

        public Builder userId(UUID val) {
            userId = new UserId(val);
            return this;
        }

        public Builder token(String val) {
            token = val;
            return this;
        }

        public Builder ipAddress(IPAddress val) {
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

        public Session build() {
            return new Session(this);
        }
    }
}
