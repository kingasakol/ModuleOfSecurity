package org.safety.library.initializationModule.abstractObjectCreators;

import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.abstractMappingObjects.DataAccess;
import org.safety.library.initializationModule.utils.DatabaseWrappers;
import org.safety.library.models.Role;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataAccessCreator {
    DatabaseWrappers databaseWrappers;

    public DataAccessCreator(DatabaseWrappers databaseWrappers){
        this.databaseWrappers = databaseWrappers;
    }

    public DataAccess createDataAccess(JSONMapping mapping){
        Map<Role, List<Integer>> result = new HashMap<>();
        String className;
        Map<String, Role> rolesMap = databaseWrappers.getRolesByItsNames();
        try{
            List<List<String>> factoryResult = mapping.getMappedData();
            className = factoryResult.get(0).get(0);
            for(int index = 1; index < factoryResult.size(); index++){
                int id = Integer.parseInt(factoryResult.get(index).get(0));
                for(int innerIndex = 1; innerIndex < factoryResult.get(index).size(); innerIndex++){
                    String roleName = factoryResult.get(index).get(innerIndex);
                    if(result.get(rolesMap.get(roleName)) == null){
                        result.put(rolesMap.get(roleName), new LinkedList<>());
                    }
                    result.get(rolesMap.get(roleName)).add(id);
                }
            }
            return new DataAccess(className, result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
