package org.safety.library.initializationModule;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.safety.library.annotations.ProtectedData;
import org.safety.library.initializationModule.abstractMappingUsers.DataAccessUser;
import org.safety.library.initializationModule.abstractMappingUsers.EntityAccessUser;
import org.safety.library.initializationModule.abstractMappingUsers.RolesForUsersUser;
import org.safety.library.initializationModule.abstractMappingUsers.RolesListUser;
import org.safety.library.initializationModule.abstractObjectCreators.DataAccessCreator;
import org.safety.library.initializationModule.abstractObjectCreators.EntityAccessCreator;
import org.safety.library.initializationModule.abstractObjectCreators.RolesForUsersCreator;
import org.safety.library.initializationModule.abstractObjectCreators.RolesListCreator;
import org.safety.library.initializationModule.mappingFactories.DataAccessJSONMapping;
import org.safety.library.initializationModule.mappingFactories.EntityAccessJSONMapping;
import org.safety.library.initializationModule.mappingFactories.RolesForUsersJSONMapping;
import org.safety.library.initializationModule.mappingFactories.RolesListJSONMapping;
import org.safety.library.initializationModule.utils.ClassFinder;
import org.safety.library.initializationModule.utils.DatabaseWrappers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

public class Initializer {
    private RolesListUser rolesListUser;
    private RolesForUsersUser rolesForUsersUser;
    private DataAccessUser dataAccessUser;
    private EntityAccessUser entityAccessUser;
    private ClassFinder classFinder;

    private Map<String, String> usersJsonPaths = new HashMap<>();

    public Initializer() throws URISyntaxException, IOException, ParseException {
        classFinder = new ClassFinder();
        initializeJsonPaths();
    }

    private void initializeJsonPaths() throws URISyntaxException, IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader(Paths.get(ClassLoader.getSystemResource("safety_library.json").toURI()).toString())){
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            String rolesListPath = (String) obj.get("path_to_the_rolesList");
            String rolesForUsersPath = (String) obj.get("path_to_the_rolesForUsers");
            String entityAccessPath = (String) obj.get("path_to_the_entityAccess");
            usersJsonPaths.put("rolesListPath", rolesListPath);
            usersJsonPaths.put("rolesForUsersPath", rolesForUsersPath);
            usersJsonPaths.put("entityAccessPath", entityAccessPath);

        }
    }

    private String getRolesPath() throws  URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource( this.usersJsonPaths.get("rolesListPath")).toURI()).toString();
    }

    private String getEntityAccessPath() throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource( this.usersJsonPaths.get("entityAccessPath")).toURI()).toString();
    }

    private String getRolesForUsersPath() throws  URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource( this.usersJsonPaths.get("rolesForUsersPath")).toURI()).toString();
    }

    private String[] getDataAccessJsonsPaths() throws IOException, URISyntaxException, ClassNotFoundException {
        List<Class> allClasses = classFinder.getAllClasses();
        List<Annotation> protectedDataAnnotations = new LinkedList<>();
        allClasses.forEach(clazz -> {
            Arrays.stream(clazz.getAnnotations()).toList().forEach(annotation -> {
                if(annotation instanceof ProtectedData){
                    protectedDataAnnotations.add(annotation);
                }
            });
        });
        return protectedDataAnnotations.stream().map(annotation -> ((ProtectedData)annotation).jsonPath()).toArray(String[]::new);
    }



    private RolesListUser initializeRolesListUser(String jsonPath) throws Exception {
        RolesListUser result = new RolesListUser(new RolesListCreator().createRolesList(new RolesListJSONMapping().read(jsonPath)));
        return result;
    }

    private RolesForUsersUser initializeRolesForUsersUser(String jsonPath) throws Exception {
        RolesForUsersUser rolesForUsersUser = new RolesForUsersUser(new RolesForUsersCreator(new DatabaseWrappers()).createRolesForUsers(new RolesForUsersJSONMapping().read(jsonPath)));
        return rolesForUsersUser;
    }

    private DataAccessUser initializeDataAccessUser(String[] jsonPaths) {
        DataAccessUser dataAccessUser = new DataAccessUser(
                Arrays.stream(jsonPaths).map(jsonPath -> {
                    try {
                        return new DataAccessCreator(new DatabaseWrappers()).createDataAccess(new DataAccessJSONMapping().read(Paths.get(ClassLoader.getSystemResource(jsonPath).toURI()).toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }).toList()
        );
        return dataAccessUser;
    }

    private EntityAccessUser initializeEntityAccessUser(String jsonPath) throws Exception{
        return new EntityAccessUser(new EntityAccessCreator(new DatabaseWrappers()).createEntityAccess(new EntityAccessJSONMapping().read(jsonPath)));
    }

    public void initialize() throws Exception {
        rolesListUser = initializeRolesListUser(this.getRolesPath());
        rolesListUser.use();

        rolesForUsersUser = initializeRolesForUsersUser(this.getRolesForUsersPath());
        rolesForUsersUser.use();

        dataAccessUser = initializeDataAccessUser(this.getDataAccessJsonsPaths());
        dataAccessUser.use();

        entityAccessUser = initializeEntityAccessUser(this.getEntityAccessPath());
        entityAccessUser.use();
    }

}
