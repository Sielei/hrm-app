package com.hrmapp.user.domain.entity;


import com.hrmapp.common.domain.entity.BaseEntity;
import com.hrmapp.common.domain.valueobject.UserId;
import com.hrmapp.user.domain.valueobject.PasswordResetId;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

public class PasswordReset extends BaseEntity<PasswordResetId> {
    private final String token;
    private final UserId userId;
    private Date expiry;

    private PasswordReset(Builder builder) {
        super.setId(builder.id);
        token = builder.token;
        userId = builder.userId;
        expiry = builder.expiry;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getToken() {
        return token;
    }

    public UserId getUserId() {
        return userId;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void createPasswordReset(){
        super.setId(new PasswordResetId(UUID.randomUUID()));
        var exp = LocalDate.now().plusDays(1);
        expiry = Date.from(exp.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    public static final class Builder {
        private PasswordResetId id;
        private String token;
        private UserId userId;
        private Date expiry;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = new PasswordResetId(val);
            return this;
        }

        public Builder token(String val) {
            token = val;
            return this;
        }

        public Builder userId(UUID val) {
            userId = new UserId(val);
            return this;
        }

        public Builder expiry(Date val) {
            expiry = val;
            return this;
        }

        public PasswordReset build() {
            return new PasswordReset(this);
        }
    }
}
