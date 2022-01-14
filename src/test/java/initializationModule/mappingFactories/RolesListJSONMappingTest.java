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


public class RolesListJSONMappingTest {

    @Nested
    class JSONReadingTest {
        private String JSONPath;
        private final RolesListJSONMapping rolesListJSONMapping = new RolesListJSONMapping();

        @BeforeEach
        public void init() throws URISyntaxException {
            JSONPath = Paths.get(ClassLoader.getSystemResource(
                    "exampleTestFiles/exampleJSONs/validJSONs/rolesListJSONFile.json").toURI()).toString();
        }

        @Test
        public void rolesMappingTest() throws Exception {
            JSONMapping jsonMapping = rolesListJSONMapping.read(JSONPath);

            assertEquals(jsonMapping.getMappedData().size(), 5);

            assertEquals(jsonMapping.getMappedData().get(0).get(0), "RolesList");
            assertEquals(jsonMapping.getMappedData().get(1).get(0), "admin");
            assertEquals(jsonMapping.getMappedData().get(2).get(0), "ksiegowy");
            assertEquals(jsonMapping.getMappedData().get(3).get(0), "tester");
            assertEquals(jsonMapping.getMappedData().get(4).get(0), "hacker");
        }

        @Test
        public void throwsExceptionIfOnlyUniqueNames() {
            assertDoesNotThrow(() -> this.rolesListJSONMapping.read(JSONPath));
        }
    }
}
