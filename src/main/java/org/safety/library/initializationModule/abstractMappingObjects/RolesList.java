package org.safety.library.initializationModule.abstractMappingObjects;

import org.safety.library.models.Role;

import java.util.List;

public class RolesList {
    private List<List<String>> roles;

    public RolesList(List<List<String>> roles) {
        this.roles = roles;
    }

    public List<List<String>> getRoles() {
        return roles;
    }
}
