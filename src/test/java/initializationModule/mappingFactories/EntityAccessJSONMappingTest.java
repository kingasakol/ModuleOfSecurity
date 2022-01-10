package initializationModule.mappingFactories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.mappingFactories.EntityAccessJSONMapping;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class EntityAccessJSONMappingTest {
    @Nested
    class JSONReadingTest {
        private String JSONPath;
        private String JSONPathWithDoubledEntityName;
        private EntityAccessJSONMapping entityAccessJSONMapping = new EntityAccessJSONMapping();

        @BeforeEach
        public void init() throws URISyntaxException {
            this.JSONPath = Paths.get(ClassLoader.getSystemResource("exampleTestFiles/exampleJSONs/validJSONs/entityAccessExampleJSONFile.json").toURI()).toString();
            this.JSONPathWithDoubledEntityName = Paths.get(ClassLoader.getSystemResource("exampleTestFiles/exampleJSONs/corruptedJSONs/entityAccessExampleJSONFileWithDoublesEntityName.json").toURI()).toString();
        }

        @Test
        public void createsArrayWithValidLength() throws Exception {
            JSONMapping result = this.entityAccessJSONMapping.read(this.JSONPath);

            assertEquals(result.getMappedData().size(), 3);
        }

        @Test
        public void createsArrayWithValidContent() throws Exception {
            JSONMapping result = this.entityAccessJSONMapping.read(this.JSONPath);

            assertArrayEquals(result.getMappedData().get(0).toArray(), new LinkedList<>(
                    Arrays.asList("JakasKlasa", "ksiegowa", "prezesi", "admin", "hacker")).toArray()
            );
            assertArrayEquals(result.getMappedData().get(1).toArray(), new LinkedList<>(
                    Arrays.asList("JakasInnaKlasa", "jakasRola", "hardkorzy", "admin", "sprzatacze")).toArray()
            );
            assertArrayEquals(result.getMappedData().get(2).toArray(), new LinkedList<>(
                    Arrays.asList("JeszczeInnaKlasa", "innaRola", "szaracy", "admin", "kolejnaRola")).toArray()
            );
        }
        @Test
        public void throwsExceptionIfJSONDoesNotContainDistinctEntityName() {
            assertThrows(Exception.class, () -> this.entityAccessJSONMapping.read(this.JSONPathWithDoubledEntityName));
            assertDoesNotThrow(() -> this.entityAccessJSONMapping.read(this.JSONPath));
        }
    }
}
