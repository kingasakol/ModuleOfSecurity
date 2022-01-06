package initializationModule.abstractObjectCreators;

import helpers.DataAccessCreatorTestStubs;
import helpers.DatabaseWrapperStubs;
import helpers.JSONMappingStubs;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.JSONMappingFactory;
import org.safety.library.initializationModule.abstractMappingObjects.DataAccess;
import org.safety.library.initializationModule.abstractObjectCreators.DataAccessCreator;
import org.safety.library.initializationModule.utils.DatabaseWrappers;
import org.safety.library.initializationModule.utils.Permission;
import org.safety.library.models.Role;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class DataAccessCreatorTest {
    Map<String, Role> stubbedRoles = DatabaseWrapperStubs.stubbedRoles;

    @Test
    public void createsValidDataAccessFromFactory() throws Exception{
        //given

        DataAccessCreator creator = new DataAccessCreator(DatabaseWrapperStubs.getDatabaseWrappersMock());

        JSONMapping mapping = JSONMappingStubs.getJSONMappingMock(DataAccessCreatorTestStubs.stubbedFactoryResults);

        //when
        DataAccess result = creator.createDataAccess(mapping);

        //then
        assertArrayEquals(result.getAccessForEntity().get(stubbedRoles.get("admin")).toArray(), new LinkedList<>(
                Arrays.asList(new Permission(stubbedRoles.get("admin"),1, true,true,true),
                              new Permission(stubbedRoles.get("admin"), 2, true, true, false),
                              new Permission(stubbedRoles.get("admin"), 3, true, true, true),
                              new Permission(stubbedRoles.get("admin"), 4, true, true, true),
                              new Permission(stubbedRoles.get("admin"), 5, true, true, true))
        ).toArray());
        assertArrayEquals(result.getAccessForEntity().get(stubbedRoles.get("tester")).toArray(), new LinkedList<>(
                Arrays.asList(new Permission(stubbedRoles.get("tester"), 3, true, true, false),
                              new Permission(stubbedRoles.get("tester"), 4, true, true, false))
        ).toArray());
        assertArrayEquals(result.getAccessForEntity().get(stubbedRoles.get("ksiegowy")).toArray(), new LinkedList<>(
                Arrays.asList(new Permission(stubbedRoles.get("ksiegowy"), 1, true, false, false),
                              new Permission(stubbedRoles.get("ksiegowy"), 5, true, false, false))
        ).toArray());
        assertArrayEquals(result.getAccessForEntity().get(stubbedRoles.get("hacker")).toArray(), new LinkedList<>(
                Arrays.asList(new Permission(stubbedRoles.get("hacker"), 2, true, true, false),
                              new Permission(stubbedRoles.get("hacker"), 5, false, false, false))
        ).toArray());
        assertArrayEquals(result.getAccessForEntity().get(stubbedRoles.get("signedOutUser")).toArray(), new LinkedList<>(
                Arrays.asList(new Permission(stubbedRoles.get("signedOutUser"), 4, true, false, false))
        ).toArray());
        assertArrayEquals(result.getAccessForEntity().get(stubbedRoles.get("prezesi")).toArray(), new LinkedList<>(
                Arrays.asList(new Permission(stubbedRoles.get("prezesi"), 2, true, true, true),
                              new Permission(stubbedRoles.get("prezesi"), 5, true, true, true))
        ).toArray());
        assertArrayEquals(result.getAccessForEntity().get(stubbedRoles.get("HR")).toArray(), new LinkedList<>(
                Arrays.asList(new Permission(stubbedRoles.get("HR"), 1, true, false, false),
                              new Permission(stubbedRoles.get("HR"), 3, true, true, false))
        ).toArray());
    }

}