package org.safety.library.initializationModule.abstractMappingUsers;

import org.safety.library.initializationModule.JSONMappingUser;
import org.safety.library.initializationModule.abstractMappingObjects.DataAccess;

import java.util.List;

public class DataAccessUser implements JSONMappingUser {
    private List<DataAccess> dataAccesses;

    public DataAccessUser(List<DataAccess> dataAccesses) {
        this.dataAccesses = dataAccesses;
    }

    @Override
    public void use() {
        this.dataAccesses.forEach(dataAccess -> {

        });
    }
}
