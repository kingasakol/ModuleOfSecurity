package org.safety.library.initializationModule.utils;

import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.Exceptions.RoleForUserNotFoundException;
import org.safety.library.models.Role;

public class Authenticator {
    private Authenticator() {}
    private long userId;
    private Role role;
    private DatabaseWrappers databaseWrappers = new DatabaseWrappers();

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
        try {
            role = databaseWrappers.getRoleByUserID(userId);
        } catch (RoleForUserNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static Authenticator getInstance(){
        return AuthenticatorHelper.instance;
    }

    private class AuthenticatorHelper{
        private static final Authenticator instance = new Authenticator();
    }

}
