package org.safety.library.initializationModule;

import org.safety.library.annotations.Users;
import org.safety.library.initializationModule.abstractMappingUsers.RolesListUser;
import org.safety.library.initializationModule.abstractObjectCreators.RolesListCreator;
import org.safety.library.initializationModule.mappingFactories.RolesListJSONMapping;
import org.safety.library.initializationModule.utils.ClassFinder;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Initializer {
    private JSONMappingUser rolesCreator;
    private List<JSONMappingUser> configurationCreators;
    private ClassFinder classFinder;

    public Initializer(){
        classFinder = new ClassFinder();
    }

    private String getRolesPathFromAnnotation() throws Exception {
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
            throw new Exception("Exactly one class should be annotated by @Users annotation");
        }
        return ((Users)usersAnnotations.get(0)).jsonPath();
    }

    private JSONMappingUser initializeRolesCreator() throws Exception {
        JSONMappingUser result = new RolesListUser(new RolesListCreator().createRolesList(new RolesListJSONMapping().read(this.getRolesPathFromAnnotation())));
        return result;
    }

    private List<JSONMappingUser> initializeConfigurationCreators(){
        //TODO
        return null;
    }

    public void initialize() throws Exception {
        rolesCreator = initializeRolesCreator();
        configurationCreators = initializeConfigurationCreators();
        //TODO
    }

}
