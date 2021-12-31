package org.safety.library.initializationModule.abstractObjectCreators;

import org.safety.library.initializationModule.JSONMappingFactory;
import org.safety.library.initializationModule.abstractMappingObjects.RolesList;

public class RolesListCreator {
    private String jsonPath;

    public RolesListCreator(String jsonPath){
        this.jsonPath = jsonPath;
    }

    public RolesList createRolesList(JSONMappingFactory factory){
        //TODO
        return null;
    }
}
