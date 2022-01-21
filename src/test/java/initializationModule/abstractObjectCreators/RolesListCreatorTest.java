package initializationModule.abstractObjectCreators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.abstractMappingObjects.RolesList;
import org.safety.library.initializationModule.abstractObjectCreators.RolesListCreator;
import org.safety.library.initializationModule.mappingFactories.RolesListJSONMapping;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class RolesListCreatorTest {
    @Nested
    class JSONReadingTest {
        private String JSONPath;
        private final RolesListJSONMapping rolesListJSONMapping = new RolesListJSONMapping();
        private final RolesListCreator rolesListCreator = new RolesListCreator();

        @BeforeEach
        public void init() throws URISyntaxException {
            JSONPath = Paths.get(ClassLoader.getSystemResource(
                    "exampleTestFiles/exampleJSONs/validJSONs/rolesListWithPrivilagesJSONFile.json").toURI()).toString();
        }

        @Test
        public void rolesListCreatorTest() throws Exception {
            JSONMapping jsonMapping = rolesListJSONMapping.read(JSONPath);
            RolesList rolesList = rolesListCreator.createRolesList(jsonMapping);
            //System.out.println(rolesList.getRoles());
            assertEquals(rolesList.getRoles().size(), 3);
            assertEquals(rolesList.getRoles().get(0).get(0), "admin");
            assertEquals(rolesList.getRoles().get(1).get(0), "ksiegowy");
            assertEquals(rolesList.getRoles().get(2).get(0), "robol");

            assertEquals(rolesList.getRoles().get(0).get(1),"Klasa");
            assertEquals(rolesList.getRoles().get(0).get(2),"true");
            assertEquals(rolesList.getRoles().get(0).get(3),"true");
            assertEquals(rolesList.getRoles().get(0).get(4),"true");

            assertEquals(rolesList.getRoles().get(0).get(5),"InnaKlasa");
            assertEquals(rolesList.getRoles().get(0).get(6),"true");
            assertEquals(rolesList.getRoles().get(0).get(7),"true");
            assertEquals(rolesList.getRoles().get(0).get(8),"true");


//            Don't know if that below should work bc there is issue with database
//            RolesListUser rolesListUser = new RolesListUser(rolesList);
//            rolesListUser.use();
        }
    }
}
