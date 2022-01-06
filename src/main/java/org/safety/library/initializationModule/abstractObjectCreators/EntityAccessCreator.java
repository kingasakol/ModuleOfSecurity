package org.safety.library.initializationModule.abstractObjectCreators;

import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.abstractMappingObjects.EntityAccess;
import org.safety.library.initializationModule.utils.DatabaseWrappers;
import org.safety.library.models.Role;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntityAccessCreator {
    DatabaseWrappers databaseWrappers;

    public EntityAccessCreator(DatabaseWrappers databaseWrappers){
        this.databaseWrappers = databaseWrappers;
    }

    public EntityAccess createEntityAccess(JSONMapping mapping){
        Map<Role, List<String>> result = new HashMap<>();
        Map<String, Role> rolesMap = databaseWrappers.getRolesByItsNames();
        List<List<String>> mappingResult = mapping.getMappedData();
        for(int i = 0; i < mappingResult.size(); i++){
            List<String> currentList = mappingResult.get(i);
            String entityName = currentList.get(0);
            for(int j = 1; j < currentList.size(); j++){
                Role role = rolesMap.get(currentList.get(j));
                if(result.get(role) == null){
                    result.put(role, new LinkedList<>());
                }
                result.get(role).add(entityName);
            }

        }
        return new EntityAccess(result);

    }
}
