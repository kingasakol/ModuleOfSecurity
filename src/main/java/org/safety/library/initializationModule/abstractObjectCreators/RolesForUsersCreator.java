package org.safety.library.initializationModule.abstractObjectCreators;

import org.safety.library.initializationModule.JSONMappingFactory;
import org.safety.library.initializationModule.abstractMappingObjects.RolesForUsers;

public class RolesForUsersCreator {
    private String jsonPath;

    public RolesForUsersCreator(String jsonPath){
        this.jsonPath = jsonPath;
    }

    public RolesForUsers createRolesForUsers(JSONMappingFactory factory){
        //TODO
        return null;
    }
}
