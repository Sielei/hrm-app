package com.hrmapp.user.domain.entity;

import com.hrmapp.common.domain.entity.BaseEntity;
import com.hrmapp.common.domain.exception.EmailException;
import com.hrmapp.common.domain.valueobject.EmployeeId;
import com.hrmapp.common.domain.valueobject.UserId;
import com.hrmapp.user.domain.exception.RoleException;
import com.hrmapp.user.domain.valueobject.PasswordPolicyId;
import com.hrmapp.user.domain.valueobject.UserStatus;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class User extends BaseEntity<UserId> {
    private final EmployeeId employeeId;
    private String username;
    private String emailAddress;
    private String password;
    private Set<Role> roles;
    private UserStatus status;
    private final UserId createdBy;
    private PasswordPolicyId passwordPolicyId;
    private Boolean changePasswordNextLogin;

    private User(Builder builder) {
        super.setId(builder.id);
        employeeId = builder.employeeId;
        username = builder.username;
        emailAddress = builder.emailAddress;
        password = builder.password;
        roles = builder.roles;
        status = builder.status;
        createdBy = builder.createdBy;
        passwordPolicyId = builder.passwordPolicyId;
        changePasswordNextLogin = builder.changePasswordNextLogin;
    }
    public static Builder builder() {
        return new Builder();
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserId getCreatedBy() {
        return createdBy;
    }

    public PasswordPolicyId getPasswordPolicyId() {
        return passwordPolicyId;
    }

    public Boolean getChangePasswordNextLogin() {
        return changePasswordNextLogin;
    }
    public void createUser(){
        verifyEmailAddress();
        setId(new UserId(UUID.randomUUID()));
        this.status = UserStatus.ACTIVE;
        this.roles = Collections.emptySet();
    }

    private void verifyEmailAddress() {
        var emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(emailAddress)){
            throw new EmailException(emailAddress + " is not a valid email address!");
        }
    }

    public void updateUser(String username, String emailAddress){
        this.username = username;
        this.emailAddress = emailAddress;
    }
    public void updatePassword(String password){
        if (changePasswordNextLogin){
            changePasswordNextLogin = false;
        }
        this.password = password;
    }
    public void assignRoles(Set<Role> roles){
        if (this.roles == null){
            this.roles = Collections.emptySet();
        }
        this.roles.addAll(roles);
    }

    public void removeRoles(Set<Role> roles){
        if (this.roles == null || this.roles.isEmpty()){
            throw new RoleException("Trying to remove roles that do not exist for this user!");
        }
        this.roles.removeAll(roles);
    }

    public void assignPasswordPolicyId(PasswordPolicyId passwordPolicyId){
        this.passwordPolicyId = passwordPolicyId;
    }

    public void deactivateUser() {
        status = UserStatus.DEACTIVATED;
    }
    public void changePasswordNextLogin(Boolean changePasswordNextLogin){
        this.changePasswordNextLogin = changePasswordNextLogin;
    }


    public static final class Builder {
        private UserId id;
        private EmployeeId employeeId;
        private String username;
        private String emailAddress;
        private String password;
        private Set<Role> roles;
        private UserStatus status;
        private UserId createdBy;
        private PasswordPolicyId passwordPolicyId;
        private Boolean changePasswordNextLogin;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = new UserId(val);
            return this;
        }

        public Builder employeeId(UUID val) {
            employeeId = new EmployeeId(val);
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder emailAddress(String val) {
            emailAddress = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder roles(Set<Role> val) {
            roles = val;
            return this;
        }

        public Builder status(UserStatus val) {
            status = val;
            return this;
        }

        public Builder createdBy(UUID val) {
            createdBy = new UserId(val);
            return this;
        }

        public Builder passwordPolicyId(UUID val) {
            passwordPolicyId = new PasswordPolicyId(val);
            return this;
        }

        public Builder changePasswordNextLogin(Boolean val) {
            changePasswordNextLogin = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
