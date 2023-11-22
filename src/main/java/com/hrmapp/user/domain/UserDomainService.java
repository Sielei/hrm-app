package com.hrmapp.user.domain;

import com.hrmapp.user.application.dto.request.UpdatePasswordPolicyRequest;
import com.hrmapp.user.domain.entity.*;

import java.util.Set;

public class UserDomainService {
    public void createPasswordReset(PasswordReset passwordReset) {
        passwordReset.createPasswordReset();
    }

    public void updateUserPassword(User user, String password) {
        user.updatePassword(password);
    }

    public Session createSession(Session session) {
        session.createSession();
        return session;
    }

    public Session terminateSession(Session session) {
        session.terminateSession();
        return session;
    }

    public void createUser(User user) {
        user.createUser();
    }

    public void updateUser(User user, String username, String emailAddress) {
        user.updateUser(username, emailAddress);
    }

    public void assignRoles(User user, Set<Role> roles) {
        user.assignRoles(roles);
    }

    public void removeRoles(User user, Set<Role> roles) {
        user.removeRoles(roles);
    }

    public void deactivateUser(User user) {
        user.deactivateUser();
    }

    public void createPasswordPolicy(PasswordPolicy passwordPolicy) {
        passwordPolicy.createPolicy();
    }

    public void updatePasswordPolicy(PasswordPolicy passwordPolicy, String name, int passwordResetDays,
                                     int numberOfCharacters, int numberOfSpecialCharacters, int numberOfNumericCharacters,
                                     int numberOfLowercaseCharacters, int numberOfUppercaseCharacters) {
        passwordPolicy.updatePolicy(name, passwordResetDays, numberOfCharacters, numberOfSpecialCharacters,
                numberOfNumericCharacters, numberOfLowercaseCharacters, numberOfUppercaseCharacters);
    }

    public void createRole(Role role) {
        role.createRole();
    }

    public void addPermissionsToRole(Role role, Set<Permission> permissions) {
        role.addPermissions(permissions);
    }

    public void removeRolePermissions(Role role, Set<Permission> permissions) {
        role.removePermission(permissions);
    }

    public void updateRole(Role role, String name, String description) {
        role.updateRole(name, description);
    }
}
