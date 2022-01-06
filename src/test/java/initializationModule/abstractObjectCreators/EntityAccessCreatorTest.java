package initializationModule.abstractObjectCreators;

import helpers.DataAccessCreatorTestStubs;
import helpers.DatabaseWrapperStubs;
import helpers.EntityAccessCreatorStubs;
import helpers.JSONMappingStubs;
import org.junit.jupiter.api.Test;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.abstractMappingObjects.EntityAccess;
import org.safety.library.initializationModule.abstractObjectCreators.EntityAccessCreator;
import org.safety.library.models.Role;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class EntityAccessCreatorTest {
    Map<String, Role> stubbedRoles = DatabaseWrapperStubs.stubbedRoles;

    @Test
    public void createsValidEntityAccessFromFactory() throws Exception{
        EntityAccessCreator creator = new EntityAccessCreator(DatabaseWrapperStubs.getDatabaseWrappersMock());
        JSONMapping mapping = JSONMappingStubs.getJSONMappingMock(EntityAccessCreatorStubs.stubbedFactoryResults);

        EntityAccess result = creator.createEntityAccess(mapping);

        assertArrayEquals(result.getAddPriviledges().get(stubbedRoles.get("admin")).toArray(), new LinkedList<>(Arrays.asList(
                "Klasa1", "Klasa2", "Klasa3", "InnaKlasa", "KolejnaKlasa"
        )).toArray());
        assertArrayEquals(result.getAddPriviledges().get(stubbedRoles.get("ksiegowy")).toArray(), new LinkedList<>(Arrays.asList(
                "Klasa1", "KolejnaKlasa"
        )).toArray());
        assertArrayEquals(result.getAddPriviledges().get(stubbedRoles.get("HR")).toArray(), new LinkedList<>(Arrays.asList(
                "Klasa1", "Klasa3"
        )).toArray());
        assertArrayEquals(result.getAddPriviledges().get(stubbedRoles.get("hacker")).toArray(), new LinkedList<>(Arrays.asList(
                "Klasa2","KolejnaKlasa"
        )).toArray());
        assertArrayEquals(result.getAddPriviledges().get(stubbedRoles.get("signedOutUser")).toArray(), new LinkedList<>(Arrays.asList(
                "InnaKlasa"
        )).toArray());
        assertArrayEquals(result.getAddPriviledges().get(stubbedRoles.get("prezesi")).toArray(), new LinkedList<>(Arrays.asList(
                "Klasa2","KolejnaKlasa"
        )).toArray());
        assertArrayEquals(result.getAddPriviledges().get(stubbedRoles.get("tester")).toArray(), new LinkedList<>(Arrays.asList(
                "Klasa3","InnaKlasa"
        )).toArray());

    }
}
