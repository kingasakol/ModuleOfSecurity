package org.safety.library.initializationModule.utils;

public class Authenticator {
    private Authenticator() {}
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static Authenticator getInstance(){
        return AuthenticatorHelper.instance;
    }

    private class AuthenticatorHelper{
        private static final Authenticator instance = new Authenticator();
    }

}
