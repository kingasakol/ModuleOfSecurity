package org.safety.library.initializationModule.abstractMappingObjects;

import org.safety.library.models.Role;

import java.util.Map;

public class RolesForUsers {

    //Integer is an user ID in database
    private Map<Integer, Role> usersRoles;

    public RolesForUsers(Map<Integer, Role> usersRoles) {
        this.usersRoles = usersRoles;
    }

    public Map<Integer, Role> getUsersRoles() {
        return usersRoles;
    }
}
