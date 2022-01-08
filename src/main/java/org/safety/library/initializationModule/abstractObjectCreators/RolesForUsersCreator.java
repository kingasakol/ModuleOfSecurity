package org.safety.library.initializationModule.abstractObjectCreators;

import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.abstractMappingObjects.RolesForUsers;
import org.safety.library.initializationModule.utils.DatabaseWrappers;
import org.safety.library.models.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RolesForUsersCreator {

    DatabaseWrappers databaseWrappers;

    public RolesForUsersCreator(DatabaseWrappers databaseWrappers) {
        this.databaseWrappers = databaseWrappers;
    }


    public RolesForUsers createRolesForUsers(JSONMapping mapping){
        Map<Integer, Role> result = new HashMap();
        Map<String, Role> rolesMap = databaseWrappers.getRolesByItsNames();

        try {
            List<List<String>> factoryResult = mapping.getMappedData();
            int resultSize = factoryResult.size();

            for(int i = 1; i < resultSize; i++) {
                int id = Integer.parseInt(factoryResult.get(i).get(0));
                String roleName = factoryResult.get(i).get(1);
                if(result.get(id) == null){
                    result.put(id, rolesMap.get(roleName));
                }
            }

            return new RolesForUsers(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
