package com.hrmapp.user.infrastructure.data;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "password_policies")
public class PasswordPolicyEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String name;
    private int passwordResetDays;
    private int numberOfCharacters;
    private int numberOfSpecialCharacters;
    private int numberOfNumericCharacters;
    private int numberOfLowercaseCharacters;
    private int numberOfUppercaseCharacters;
    @OneToMany(mappedBy = "passwordPolicy", fetch = FetchType.LAZY)
    private List<UserEntity> users;
    private UUID createdBy;

    @OneToMany(mappedBy = "passwordPolicy", orphanRemoval = true)
    private Set<UserEntity> userEntities = new LinkedHashSet<>();

    public PasswordPolicyEntity() {
    }

    private PasswordPolicyEntity(Builder builder) {
        id = builder.id;
        name = builder.name;
        passwordResetDays = builder.passwordResetDays;
        numberOfCharacters = builder.numberOfCharacters;
        numberOfSpecialCharacters = builder.numberOfSpecialCharacters;
        numberOfNumericCharacters = builder.numberOfNumericCharacters;
        numberOfLowercaseCharacters = builder.numberOfLowercaseCharacters;
        numberOfUppercaseCharacters = builder.numberOfUppercaseCharacters;
        users = builder.users;
        createdBy = builder.createdBy;
        userEntities = builder.userEntities;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPasswordResetDays() {
        return passwordResetDays;
    }

    public int getNumberOfCharacters() {
        return numberOfCharacters;
    }

    public int getNumberOfSpecialCharacters() {
        return numberOfSpecialCharacters;
    }

    public int getNumberOfNumericCharacters() {
        return numberOfNumericCharacters;
    }

    public int getNumberOfLowercaseCharacters() {
        return numberOfLowercaseCharacters;
    }

    public int getNumberOfUppercaseCharacters() {
        return numberOfUppercaseCharacters;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public Set<UserEntity> getUserEntities() {
        return userEntities;
    }


    public static final class Builder {
        private UUID id;
        private String name;
        private int passwordResetDays;
        private int numberOfCharacters;
        private int numberOfSpecialCharacters;
        private int numberOfNumericCharacters;
        private int numberOfLowercaseCharacters;
        private int numberOfUppercaseCharacters;
        private List<UserEntity> users;
        private UUID createdBy;
        private Set<UserEntity> userEntities;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder passwordResetDays(int val) {
            passwordResetDays = val;
            return this;
        }

        public Builder numberOfCharacters(int val) {
            numberOfCharacters = val;
            return this;
        }

        public Builder numberOfSpecialCharacters(int val) {
            numberOfSpecialCharacters = val;
            return this;
        }

        public Builder numberOfNumericCharacters(int val) {
            numberOfNumericCharacters = val;
            return this;
        }

        public Builder numberOfLowercaseCharacters(int val) {
            numberOfLowercaseCharacters = val;
            return this;
        }

        public Builder numberOfUppercaseCharacters(int val) {
            numberOfUppercaseCharacters = val;
            return this;
        }

        public Builder users(List<UserEntity> val) {
            users = val;
            return this;
        }

        public Builder createdBy(UUID val) {
            createdBy = val;
            return this;
        }

        public Builder userEntities(Set<UserEntity> val) {
            userEntities = val;
            return this;
        }

        public PasswordPolicyEntity build() {
            return new PasswordPolicyEntity(this);
        }
    }
}