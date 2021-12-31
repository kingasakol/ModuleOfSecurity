package org.safety.library.initializationModule.abstractObjectCreators;

import org.safety.library.initializationModule.JSONMappingFactory;
import org.safety.library.initializationModule.abstractMappingObjects.DataAccess;

public class DataAccessCreator {
    private String jsonPath;

    public DataAccessCreator(String jsonPath){
        this.jsonPath = jsonPath;
    }

    public DataAccess createDataAccess(JSONMappingFactory factory){
        //TODO
        return null;
    }

}
