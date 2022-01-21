package org.safety.library.initializationModule.abstractObjectCreators;

import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.abstractMappingObjects.RolesList;
import org.safety.library.models.Role;

import java.util.*;


public class RolesListCreator {

    public RolesList createRolesList(JSONMapping mapping){
//        List<String> rawStringList = mapping.getMappedData().stream().map(list -> list.get(0)).toList();
//        List<String> stringList = new ArrayList<>(rawStringList);
//        stringList.remove(0);
//        // Remove "RolesList" (name JSONFile)
//        // TODO
//        List<List<String>> rolesList = stringList.stream().map(List<String>::new).toList();
        List<List<String>> rolesList = mapping.getMappedData();
        rolesList.remove(0);
        return new RolesList(rolesList);
    }

}
