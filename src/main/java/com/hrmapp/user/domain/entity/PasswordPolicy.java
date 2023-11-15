package com.hrmapp.user.domain.entity;

import com.hrmapp.common.domain.entity.BaseEntity;
import com.hrmapp.user.domain.exception.PasswordPolicyException;
import com.hrmapp.user.domain.valueobject.PasswordPolicyId;

import java.util.Set;
import java.util.UUID;

public class PasswordPolicy extends BaseEntity<PasswordPolicyId> {
    private String name;
    private int passwordResetDays;
    private int numberOfCharacters;
    private int numberOfSpecialCharacters;
    private int numberOfNumericCharacters;
    private int numberOfLowercaseCharacters;
    private int numberOfUppercaseCharacters;
    private Set<User> users;

    private PasswordPolicy(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        passwordResetDays = builder.passwordResetDays;
        numberOfCharacters = builder.numberOfCharacters;
        numberOfSpecialCharacters = builder.numberOfSpecialCharacters;
        numberOfNumericCharacters = builder.numberOfNumericCharacters;
        numberOfLowercaseCharacters = builder.numberOfLowercaseCharacters;
        numberOfUppercaseCharacters = builder.numberOfUppercaseCharacters;
        users = builder.users;
    }
    public static Builder builder() {
        return new Builder();
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

    public Set<User> getUsers() {
        return users;
    }

    public void createPolicy(){
        verifyNumberOfCharacters();
        setId(new PasswordPolicyId(UUID.randomUUID()));
    }

    private void verifyNumberOfCharacters() {
        var actualNumberOfCharacters = numberOfSpecialCharacters + numberOfNumericCharacters + numberOfLowercaseCharacters + numberOfUppercaseCharacters;
        if (numberOfCharacters < actualNumberOfCharacters){
            throw new PasswordPolicyException("Total sum of special characters, numeric characters, lowercase characters" +
                    " and uppercase characters is: " + actualNumberOfCharacters + " which exceeds the maximum number of " +
                    "characters specified: " + numberOfCharacters);
        }
    }

    public void updatePolicy(String name, int passwordResetDays, int numberOfCharacters, int numberOfSpecialCharacters,
                             int numberOfNumericCharacters, int numberOfLowercaseCharacters, int numberOfUppercaseCharacters){
        this.name = name;
        this.passwordResetDays = passwordResetDays;
        this.numberOfCharacters = numberOfCharacters;
        this.numberOfSpecialCharacters = numberOfSpecialCharacters;
        this.numberOfNumericCharacters = numberOfNumericCharacters;
        this.numberOfLowercaseCharacters = numberOfLowercaseCharacters;
        this.numberOfUppercaseCharacters = numberOfUppercaseCharacters;
    }


    public static final class Builder {
        private PasswordPolicyId id;
        private String name;
        private int passwordResetDays;
        private int numberOfCharacters;
        private int numberOfSpecialCharacters;
        private int numberOfNumericCharacters;
        private int numberOfLowercaseCharacters;
        private int numberOfUppercaseCharacters;
        private Set<User> users;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = new PasswordPolicyId(val);
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

        public Builder users(Set<User> val) {
            users = val;
            return this;
        }

        public PasswordPolicy build() {
            return new PasswordPolicy(this);
        }
    }
}
