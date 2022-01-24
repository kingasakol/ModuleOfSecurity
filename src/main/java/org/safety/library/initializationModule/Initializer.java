package org.safety.library.initializationModule;

import org.hibernate.Session;
import org.safety.library.annotations.ProtectedData;
import org.safety.library.annotations.Users;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.Exceptions.ClassWithAnnotationDidntFound;
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
import org.safety.library.models.Role;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Initializer {
    private RolesListUser rolesListUser;
    private RolesForUsersUser rolesForUsersUser;
    private DataAccessUser dataAccessUser;
    private EntityAccessUser entityAccessUser;
    private ClassFinder classFinder;

    public Initializer(){
        classFinder = new ClassFinder();
    }

    private Users getUsersAnnotation() throws ClassWithAnnotationDidntFound, IOException, URISyntaxException, ClassNotFoundException {
        List<Class> allClasses = classFinder.getAllClasses();
        List<Annotation> usersAnnotations = new LinkedList<>();
        allClasses.forEach(clazz -> {
            Arrays.stream(clazz.getAnnotations()).toList().forEach(annotation -> {
                if(annotation instanceof Users){
                    usersAnnotations.add(annotation);
                }
            });
        });
        if(usersAnnotations.size() != 1){
            throw new ClassWithAnnotationDidntFound("Didn't found any class annotated by Annotation @Users. To use safety library" +
                    " you must annotate Hibernate entity, that represents Users entity with annotation" +
                    " @Users.");
        }
        return ((Users)usersAnnotations.get(0));
    }

    private String getRolesPathFromAnnotation() throws ClassWithAnnotationDidntFound, IOException, URISyntaxException, ClassNotFoundException {
        return Paths.get(ClassLoader.getSystemResource( this.getUsersAnnotation().rolesListJsonPath()).toURI()).toString();
    }

    private String getEntityAccessPathFromAnnotation() throws ClassWithAnnotationDidntFound, IOException, URISyntaxException, ClassNotFoundException {
        return Paths.get(ClassLoader.getSystemResource( this.getUsersAnnotation().entityAccessJsonPath()).toURI()).toString();
    }

    private String getRolesForUsersPathFromAnnotation() throws ClassWithAnnotationDidntFound, IOException, URISyntaxException, ClassNotFoundException {
        return Paths.get(ClassLoader.getSystemResource( this.getUsersAnnotation().rolesForUsersJsonPath()).toURI()).toString();
    }

    private String[] getDataAccessJsonsPathsFromAnnotations() throws IOException, URISyntaxException, ClassNotFoundException {
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

    private DataAccessUser initializeDataAccessUser(String[] jsonPaths) throws Exception {
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
        rolesListUser = initializeRolesListUser(this.getRolesPathFromAnnotation());
        rolesListUser.use();

        rolesForUsersUser = initializeRolesForUsersUser(this.getRolesForUsersPathFromAnnotation());
        rolesForUsersUser.use();

        dataAccessUser = initializeDataAccessUser(this.getDataAccessJsonsPathsFromAnnotations());
        dataAccessUser.use();

        entityAccessUser = initializeEntityAccessUser(this.getEntityAccessPathFromAnnotation());
        entityAccessUser.use();
    }

}
