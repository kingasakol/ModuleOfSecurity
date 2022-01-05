package org.safety.library.initializationModule.abstractObjectCreators;

import org.hibernate.Session;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.JSONMappingFactory;
import org.safety.library.initializationModule.abstractMappingObjects.DataAccess;
import org.safety.library.models.Role;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataAccessCreator {
    private String jsonPath;

    public DataAccessCreator(String jsonPath){
        this.jsonPath = jsonPath;
    }

    public DataAccess createDataAccess(JSONMappingFactory factory){
        Map<Role, List<Integer>> result = new HashMap<>();
        String className;
        Map<String, Role> rolesMap = getRolesByItsNames();
        try{
            List<List<String>> factoryResult = factory.read(jsonPath).getMappedData();
            className = factoryResult.get(0).get(0);
            for(int index = 1; index < factoryResult.size(); index++){
                int id = Integer.parseInt(factoryResult.get(index).get(0));
                for(int innerIndex = 1; innerIndex < factoryResult.get(index).size(); innerIndex++){
                    String roleName = factoryResult.get(index).get(innerIndex);
                    if(result.get(rolesMap.get(roleName)) == null){
                        result.put(rolesMap.get(rolesMap), new LinkedList<>());
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

    private Map<String, Role> getRolesByItsNames(){
        Session session = SessionProvider.getSession();
        Map<String, Role> result = new HashMap<>();
        List<Role> queriedRoles = session.createQuery("FROM Role R").list();
        queriedRoles.forEach(role -> {
            result.put(role.getName(), role);
        });
        return result;
    }


}
