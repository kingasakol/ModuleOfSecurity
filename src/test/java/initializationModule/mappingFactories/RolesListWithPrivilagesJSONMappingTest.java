package initializationModule.mappingFactories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.mappingFactories.RolesListJSONMapping;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RolesListWithPrivilagesJSONMappingTest {
    @Nested
    class JSONReadingTest {
        private String JSONPath;
        private final RolesListJSONMapping rolesListJSONMapping = new RolesListJSONMapping();

        @BeforeEach
        public void init() throws URISyntaxException {
            JSONPath = Paths.get(ClassLoader.getSystemResource(
                    "exampleTestFiles/exampleJSONs/validJSONs/rolesListWithPrivilagesJSONFile.json").toURI()).toString();
        }

        @Test
        public void rolesMappingTest() throws Exception {
            JSONMapping jsonMapping = rolesListJSONMapping.read(JSONPath);

            assertEquals(jsonMapping.getMappedData().size(), 4);

            assertEquals(jsonMapping.getMappedData().get(0).get(0), "RolesList");
            assertEquals(jsonMapping.getMappedData().get(1).get(0), "admin");
            assertEquals(jsonMapping.getMappedData().get(2).get(0), "ksiegowy");
            assertEquals(jsonMapping.getMappedData().get(3).get(0), "robol");

            assertEquals(jsonMapping.getMappedData().get(1).get(1), "Klasa");
            assertEquals(jsonMapping.getMappedData().get(1).get(2), "true");
            assertEquals(jsonMapping.getMappedData().get(1).get(3), "true");
            assertEquals(jsonMapping.getMappedData().get(1).get(4), "true");

            assertEquals(jsonMapping.getMappedData().get(1).get(5), "InnaKlasa");
            assertEquals(jsonMapping.getMappedData().get(1).get(6), "true");
            assertEquals(jsonMapping.getMappedData().get(1).get(7), "true");
            assertEquals(jsonMapping.getMappedData().get(1).get(8), "true");

            assertEquals(jsonMapping.getMappedData().get(2).get(1), "Klasa");
            assertEquals(jsonMapping.getMappedData().get(2).get(2), "true");
            assertEquals(jsonMapping.getMappedData().get(2).get(3), "false");
            assertEquals(jsonMapping.getMappedData().get(2).get(4), "false");

            assertEquals(jsonMapping.getMappedData().get(2).get(5), "InnaKlasa");
            assertEquals(jsonMapping.getMappedData().get(2).get(6), "true");
            assertEquals(jsonMapping.getMappedData().get(2).get(7), "true");
            assertEquals(jsonMapping.getMappedData().get(2).get(8), "false");


            assertEquals(jsonMapping.getMappedData().get(3).get(1), "Klasa");
            assertEquals(jsonMapping.getMappedData().get(3).get(2), "true");
            assertEquals(jsonMapping.getMappedData().get(3).get(3), "false");
            assertEquals(jsonMapping.getMappedData().get(3).get(4), "false");

            assertEquals(jsonMapping.getMappedData().get(3).get(5), "InnaKlasa");
            assertEquals(jsonMapping.getMappedData().get(3).get(6), "true");
            assertEquals(jsonMapping.getMappedData().get(3).get(7), "false");
            assertEquals(jsonMapping.getMappedData().get(3).get(8), "false");

        }

        @Test
        public void throwsExceptionIfOnlyUniqueNames() {
            assertDoesNotThrow(() -> this.rolesListJSONMapping.read(JSONPath));
        }
    }
}
