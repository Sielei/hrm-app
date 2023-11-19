package com.hrmapp.user.domain;

import com.hrmapp.user.domain.entity.PasswordReset;
import com.hrmapp.user.domain.entity.Session;
import com.hrmapp.user.domain.entity.User;

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
}
