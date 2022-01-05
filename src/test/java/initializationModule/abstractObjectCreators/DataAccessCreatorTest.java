package initializationModule.abstractObjectCreators;

import helpers.DataAccessCreatorTestStubs;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.JSONMappingFactory;
import org.safety.library.initializationModule.abstractMappingObjects.DataAccess;
import org.safety.library.initializationModule.abstractObjectCreators.DataAccessCreator;
import org.safety.library.initializationModule.utils.DatabaseWrappers;
import org.safety.library.models.Role;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class DataAccessCreatorTest {
    Map<String, Role> stubbedRoles = DataAccessCreatorTestStubs.stubbedRoles;

    @Test
    public void createsValidDataAccessFromFactory() throws Exception{
        //given

        DataAccessCreator creator = new DataAccessCreator(DataAccessCreatorTestStubs.getDatabaseWrappersMock());

        JSONMapping mapping = DataAccessCreatorTestStubs.getJSONMappingMock();

        //when
        DataAccess result = creator.createDataAccess(mapping);

        //then
        assertArrayEquals(result.getAccessForEntity().get(stubbedRoles.get("admin")).toArray(), new LinkedList<>(
                Arrays.asList(1,2,3,4,5)
        ).toArray());
        assertArrayEquals(result.getAccessForEntity().get(stubbedRoles.get("tester")).toArray(), new LinkedList<>(
                Arrays.asList(3,4)
        ).toArray());
        assertArrayEquals(result.getAccessForEntity().get(stubbedRoles.get("ksiegowy")).toArray(), new LinkedList<>(
                Arrays.asList(1,5)
        ).toArray());
        assertArrayEquals(result.getAccessForEntity().get(stubbedRoles.get("hacker")).toArray(), new LinkedList<>(
                Arrays.asList(2,5)
        ).toArray());
        assertArrayEquals(result.getAccessForEntity().get(stubbedRoles.get("signedOutUser")).toArray(), new LinkedList<>(
                Arrays.asList(4)
        ).toArray());
        assertArrayEquals(result.getAccessForEntity().get(stubbedRoles.get("prezesi")).toArray(), new LinkedList<>(
                Arrays.asList(2,5)
        ).toArray());
        assertArrayEquals(result.getAccessForEntity().get(stubbedRoles.get("HR")).toArray(), new LinkedList<>(
                Arrays.asList(1,3)
        ).toArray());
    }

}