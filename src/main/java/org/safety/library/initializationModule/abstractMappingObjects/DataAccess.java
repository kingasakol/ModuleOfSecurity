package org.safety.library.initializationModule.abstractMappingObjects;

import org.safety.library.models.Role;

import java.util.List;
import java.util.Map;

public class DataAccess {

    //Integer is an ID of protected record
    //String is a protected entity name
    private Map<String, Map<Role, List<Integer>>>  accessForEntity;

    public DataAccess(Map<String, Map<Role, List<Integer>>> accessForEntity) {
        this.accessForEntity = accessForEntity;
    }

    public Map<String, Map<Role, List<Integer>>> getAccessForEntity() {
        return accessForEntity;
    }
}
