package helpers;

import org.mockito.Mockito;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.JSONMappingFactory;
import org.safety.library.initializationModule.abstractObjectCreators.DataAccessCreator;
import org.safety.library.initializationModule.utils.DatabaseWrappers;
import org.safety.library.models.Role;

import java.util.*;

public class DataAccessCreatorTestStubs {
    public static Map<String, Role> stubbedRoles = new HashMap<String, Role>() {{
        put("admin", new Role("admin"));
        put("tester", new Role("tester"));
        put("ksiegowy", new Role("ksiegowy"));
        put("hacker", new Role("hacker"));
        put("signedOutUser", new Role("signedOutUser"));
        put("prezesi", new Role("prezesi"));
        put("HR", new Role("HR"));
    }};

    public static List<List<String>> stubbedFactoryResults = new LinkedList<>(Arrays.asList(
            new LinkedList<>(Arrays.asList("SomeClass")),
            new LinkedList<>(Arrays.asList("1", "admin","ksiegowy", "HR")),
            new LinkedList<>(Arrays.asList("2", "admin", "hacker", "prezesi")),
            new LinkedList<>(Arrays.asList("3", "admin", "tester", "HR")),
            new LinkedList<>(Arrays.asList("4", "admin", "signedOutUser", "tester")),
            new LinkedList<>(Arrays.asList("5", "admin", "hacker", "ksiegowy", "prezesi"))
    ));

    public static JSONMapping getJSONMappingMock(){
        JSONMapping mapping = Mockito.mock(JSONMapping.class);
        Mockito.when(mapping.getMappedData()).thenReturn(DataAccessCreatorTestStubs.stubbedFactoryResults);
        return mapping;
    }

    public static DatabaseWrappers getDatabaseWrappersMock(){
        DatabaseWrappers databaseWrappers = Mockito.mock(DatabaseWrappers.class);
        Mockito.when(databaseWrappers.getRolesByItsNames()).thenReturn(stubbedRoles);
        return databaseWrappers;
    }

}
