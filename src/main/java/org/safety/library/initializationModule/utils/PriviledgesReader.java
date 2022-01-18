package org.safety.library.initializationModule.utils;

import org.safety.library.initializationModule.Exceptions.RoleForUserNotFoundException;
import org.safety.library.models.Role;

public class PriviledgesReader {
    private DatabaseWrappers wrappers;

    public PriviledgesReader(DatabaseWrappers wrappers){
        this.wrappers = wrappers;
    }

    public Role getRole() throws RoleForUserNotFoundException {
        Authenticator authenticator = Authenticator.getInstance();
        return wrappers.getRoleByUserID(authenticator.getUserId());
    }
}
