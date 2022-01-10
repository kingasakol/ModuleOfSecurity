package helpers;

import org.mockito.Mockito;
import org.safety.library.initializationModule.utils.DatabaseWrappers;
import org.safety.library.models.Role;

import java.util.HashMap;
import java.util.Map;

public class DatabaseWrapperStubs {
    public static Map<String, Role> stubbedRoles = new HashMap<String, Role>() {{
        put("admin", new Role("admin"));
        put("tester", new Role("tester"));
        put("ksiegowy", new Role("ksiegowy"));
        put("hacker", new Role("hacker"));
        put("signedOutUser", new Role("signedOutUser"));
        put("prezesi", new Role("prezesi"));
        put("HR", new Role("HR"));
    }};

    public static DatabaseWrappers getDatabaseWrappersMock(){
        DatabaseWrappers databaseWrappers = Mockito.mock(DatabaseWrappers.class);
        Mockito.when(databaseWrappers.getRolesByItsNames()).thenReturn(DatabaseWrapperStubs.stubbedRoles);
        return databaseWrappers;
    }
}
