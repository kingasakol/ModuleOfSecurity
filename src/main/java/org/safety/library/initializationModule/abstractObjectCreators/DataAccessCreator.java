package org.safety.library.initializationModule.abstractObjectCreators;

import org.hibernate.Session;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.JSONMappingFactory;
import org.safety.library.initializationModule.abstractMappingObjects.DataAccess;
import org.safety.library.models.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAccessCreator {
    private String jsonPath;

    public DataAccessCreator(String jsonPath){
        this.jsonPath = jsonPath;
    }

    public DataAccess createDataAccess(JSONMappingFactory factory){
        Map<String, Map<Role, List<Integer>>> result = new HashMap<>();
        List<List<String>> readData = null;
        try{
            factory.read(jsonPath).getMappedData().forEach(innerList -> {

            });

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Map<String, Role> getRolesByItsNames(){
        Session session = SessionProvider.getSession();
        Map<String, Role> result = new HashMap<>();
        session.createQuery("FROM Role R");
        return result;
    }


}
