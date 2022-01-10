package initializationModule.mappingFactories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.mappingFactories.DataAccessJSONMapping;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class DataAccessJSONMappingTest {
    @Nested
    class JSONReadingTest{
        private String JSONPath;
        private String JSONPathWithDoubledID;
        private DataAccessJSONMapping dataAccessJSONMapping = new DataAccessJSONMapping();

        @BeforeEach
        public void init() throws URISyntaxException {
            this.JSONPath = Paths.get(ClassLoader.getSystemResource("exampleTestFiles/exampleJSONs/validJSONs/dataAccessExampleJSONFile.json").toURI()).toString();
            this.JSONPathWithDoubledID = Paths.get(ClassLoader.getSystemResource("exampleTestFiles/exampleJSONs/corruptedJSONs/dataAccessExampleJSONFileWithDoubledID.json").toURI()).toString();
        }

        @Test
        public void createsArrayWithValidLength() throws Exception {
            JSONMapping result = this.dataAccessJSONMapping.read(this.JSONPath);

            assertEquals(result.getMappedData().size(), 4);
        }

        @Test
        public void createsArrayWithValidContent() throws Exception {
            JSONMapping result = this.dataAccessJSONMapping.read(this.JSONPath);

            assertArrayEquals(result.getMappedData().get(0).toArray(), new LinkedList<>(Arrays.asList("SomeProtectedEntity")).toArray());
            assertArrayEquals(result.getMappedData().get(1).toArray(), new LinkedList<>(
                    Arrays.asList("1", "admin","true","true","true", "ksiegowy", "true", "false", "false", "tester","true","true", "false", "hacker", "false", "false", "false")).toArray()
            );
            assertArrayEquals(result.getMappedData().get(2).toArray(), new LinkedList<>(
                    Arrays.asList("2", "admin","true","true","false", "signedOutUser","true","false","true", "prezesi","true","true","true")).toArray()
            );
            assertArrayEquals(result.getMappedData().get(3).toArray(), new LinkedList<>(
                    Arrays.asList("5", "admin","true","true","false", "signedOutUser", "true", "false", "false", "HR","true", "false", "false")).toArray()
            );
        }

        @Test
        public void throwsExceptionIfJSONDoesNotContainDistinctIDs() {
            assertThrows(Exception.class, () -> this.dataAccessJSONMapping.read(this.JSONPathWithDoubledID));
            assertDoesNotThrow(() -> this.dataAccessJSONMapping.read(this.JSONPath));
        }
    }

}
