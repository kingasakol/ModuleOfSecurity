package org.safety.library.initializationModule;

import java.util.List;

public class Initializer {
    private JSONMappingUser rolesCreator;
    private List<JSONMappingUser> configurationCreators;

    private JSONMappingUser initializeRolesCreator(){
        //TODO
        return null;
    }

    private List<JSONMappingUser> initializeConfigurationCreators(){
        //TODO
        return null;
    }

    public void initialize(){
        rolesCreator = initializeRolesCreator();
        configurationCreators = initializeConfigurationCreators();
        //TODO
    }

}
