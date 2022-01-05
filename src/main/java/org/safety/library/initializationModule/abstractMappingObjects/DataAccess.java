package org.safety.library.initializationModule.abstractMappingObjects;

import org.safety.library.models.Role;

import java.util.List;
import java.util.Map;

public class DataAccess {
    private String classType;

    //Integer is an ID of protected record
    private Map<Role, List<Integer>> accessForEntity;

    public DataAccess(String classType, Map<Role, List<Integer>> accessForEntity) {
        this.classType = classType;
        this.accessForEntity = accessForEntity;
    }

    public String getClassType() {
        return classType;
    }

    public Map<Role, List<Integer>> getAccessForEntity() {
        return accessForEntity;
    }
}
