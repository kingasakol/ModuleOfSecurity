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
                    "exampleTestFiles/exampleJSONs/validJSONs/rolesListJSONFile.json").toURI()).toString();
        }

        @Test
        public void rolesListCreatorTest() throws Exception {
            JSONMapping jsonMapping = rolesListJSONMapping.read(JSONPath);
            RolesList rolesList = rolesListCreator.createRolesList(jsonMapping);

            assertEquals(rolesList.getRoles().size(), 4);
            assertEquals(rolesList.getRoles().get(0).getName(), "admin");
            assertEquals(rolesList.getRoles().get(1).getName(), "ksiegowy");
            assertEquals(rolesList.getRoles().get(2).getName(), "tester");
            assertEquals(rolesList.getRoles().get(3).getName(), "hacker");

//            Don't know if that below should work bc there is issue with database
//            RolesListUser rolesListUser = new RolesListUser(rolesList);
//            rolesListUser.use();
        }
    }
}
