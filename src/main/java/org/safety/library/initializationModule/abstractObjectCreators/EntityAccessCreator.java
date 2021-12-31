package org.safety.library.initializationModule.abstractObjectCreators;

import org.safety.library.initializationModule.JSONMappingFactory;
import org.safety.library.initializationModule.abstractMappingObjects.EntityAccess;

public class EntityAccessCreator {
    private String jsonPath;

    public EntityAccessCreator(String jsonPath) {
        this.jsonPath = jsonPath;
    }


    public EntityAccess createEntityAccess(JSONMappingFactory factory){
        //TODO
        return null;
    }
}
