package org.safety.library.initializationModule.abstractMappingObjects;

import org.safety.library.models.Role;

import java.util.List;

public class RolesList {
    private List<Role> roles;

    public RolesList(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
