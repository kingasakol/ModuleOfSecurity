package initializationModule.mappingFactories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.mappingFactories.RolesForUsersJSONMapping;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class RolesForUsersJSONMappingTest {
    @Nested
    class JSONReadingTest{
        private String JSONPath;
        private RolesForUsersJSONMapping rolesForUsersJSONMapping = new RolesForUsersJSONMapping();

        @BeforeEach
        public void init() throws URISyntaxException {
            this.JSONPath = Paths.get(ClassLoader.getSystemResource("exampleTestFiles/dataExampleRolesForUsersJSONFile.json").toURI()).toString();
        }

        @Test
        public void createsArrayWithValidLength() throws Exception {
            JSONMapping result = this.rolesForUsersJSONMapping.read(this.JSONPath);

            assertEquals(result.getMappedData().size(), 5);
        }

        @Test
        public void createsArrayWithValidContent() throws Exception {
            JSONMapping result = this.rolesForUsersJSONMapping.read(this.JSONPath);

            assertArrayEquals(result.getMappedData().get(0).toArray(), new LinkedList<>(Arrays.asList("RolesForUser")).toArray());
            assertArrayEquals(result.getMappedData().get(1).toArray(), new LinkedList<>(Arrays.asList("1", "admin")).toArray());
            assertArrayEquals(result.getMappedData().get(2).toArray(), new LinkedList<>(Arrays.asList("2", "ksiegowy")).toArray());
            assertArrayEquals(result.getMappedData().get(3).toArray(), new LinkedList<>(Arrays.asList("3", "tester")).toArray());
            assertArrayEquals(result.getMappedData().get(4).toArray(), new LinkedList<>(Arrays.asList("4", "hacker")).toArray());
        }

        @Test
        public void throwsExceptionIfJSONDoesNotContainDistinctIDs() {
            assertDoesNotThrow(() -> this.rolesForUsersJSONMapping.read(this.JSONPath));
        }
    }
}
