package org.safety.library.initializationModule.abstractMappingObjects;

import org.safety.library.models.Role;

import java.util.List;
import java.util.Map;

public class EntityAccess {

    //String is a protected class Name
    private Map<Role, List<String>> addPriviledges;


    public EntityAccess(Map<Role, List<String>> addPriviledges) {
        this.addPriviledges = addPriviledges;
    }

    public Map<Role, List<String>> getAddPriviledges() {
        return addPriviledges;
    }
}
