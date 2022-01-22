package org.safety.library.initializationModule.utils;

import org.safety.library.initializationModule.Exceptions.RoleForUserNotFoundException;
import org.safety.library.models.Role;

public class PrivilegesReader {
    public static Role getRole(DatabaseWrappers databaseWrappers) throws RoleForUserNotFoundException {
        Authenticator authenticator = Authenticator.getInstance();
        return databaseWrappers.getRoleByUserID(authenticator.getUserId());
    }
}
