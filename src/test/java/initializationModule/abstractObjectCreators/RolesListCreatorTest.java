package initializationModule.abstractObjectCreators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.abstractMappingObjects.RolesList;
import org.safety.library.initializationModule.abstractMappingUsers.RolesListUser;
import org.safety.library.initializationModule.abstractObjectCreators.RolesListCreator;
import org.safety.library.initializationModule.mappingFactories.RolesListJSONMapping;
import org.safety.library.models.DefaultPrivilige;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

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
            assertEquals(rolesList.getRoles().get(0).getName(), "admin");
            assertEquals(rolesList.getRoles().get(1).getName(), "ksiegowy");
            assertEquals(rolesList.getRoles().get(2).getName(), "robol");

            List<DefaultPrivilige> adminPrivs = rolesList.getRoles().get(0).getDefaultPriviliges();
            List<DefaultPrivilige> ksiegowyPrivs = rolesList.getRoles().get(1).getDefaultPriviliges();
            List<DefaultPrivilige> robolPrivs = rolesList.getRoles().get(2).getDefaultPriviliges();

            DefaultPrivilige adminKlasa = adminPrivs.get(0);
            DefaultPrivilige adminInnaKlasa = adminPrivs.get(1);

            DefaultPrivilige ksiegowyKlasa = ksiegowyPrivs.get(0);
            DefaultPrivilige ksiegowyInnaKlasa = ksiegowyPrivs.get(1);

            DefaultPrivilige robolKlasa = robolPrivs.get(0);
            DefaultPrivilige robolInnaKlasa = robolPrivs.get(1);
            // asserting role names are correct
            assertEquals(adminKlasa.getRole().getName(), "admin");
            assertEquals(adminInnaKlasa.getRole().getName(), "admin");

            assertEquals(ksiegowyKlasa.getRole().getName(), "ksiegowy");
            assertEquals(ksiegowyInnaKlasa.getRole().getName(), "ksiegowy");

            assertEquals(robolKlasa.getRole().getName(), "robol");
            assertEquals(robolInnaKlasa.getRole().getName(), "robol");
            // asserting containing priviliges are valid
            assertEquals(adminKlasa.isCanDelete(), true);
            assertEquals(adminKlasa.isCanRead(), true);
            assertEquals(adminKlasa.isCanUpdate(), true);

            assertEquals(adminInnaKlasa.isCanDelete(), true);
            assertEquals(adminInnaKlasa.isCanRead(), true);
            assertEquals(adminInnaKlasa.isCanUpdate(), true);


            assertEquals(ksiegowyKlasa.isCanDelete(), false);
            assertEquals(ksiegowyKlasa.isCanRead(), true);
            assertEquals(ksiegowyKlasa.isCanUpdate(), false);

            assertEquals(ksiegowyInnaKlasa.isCanDelete(), false);
            assertEquals(ksiegowyInnaKlasa.isCanRead(), true);
            assertEquals(ksiegowyInnaKlasa.isCanUpdate(), true);

            assertEquals(robolKlasa.isCanDelete(), false);
            assertEquals(robolKlasa.isCanRead(), true);
            assertEquals(robolKlasa.isCanUpdate(), false);

            assertEquals(robolInnaKlasa.isCanDelete(), false);
            assertEquals(robolInnaKlasa.isCanRead(), true);
            assertEquals(robolInnaKlasa.isCanUpdate(), false);
        }
    }
}
