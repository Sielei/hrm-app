package com.hrmapp.user.infrastructure.data;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "password_resets")
public class PasswordResetEntity {
    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String token;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;
    private Date expiry;

    public PasswordResetEntity() {
    }

    private PasswordResetEntity(Builder builder) {
        id = builder.id;
        token = builder.token;
        user = builder.user;
        expiry = builder.expiry;
    }
    public static Builder builder() {
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public UserEntity getUser() {
        return user;
    }

    public Date getExpiry() {
        return expiry;
    }


    public static final class Builder {
        private UUID id;
        private String token;
        private UserEntity user;
        private Date expiry;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder token(String val) {
            token = val;
            return this;
        }

        public Builder user(UserEntity val) {
            user = val;
            return this;
        }

        public Builder expiry(Date val) {
            expiry = val;
            return this;
        }

        public PasswordResetEntity build() {
            return new PasswordResetEntity(this);
        }
    }
}