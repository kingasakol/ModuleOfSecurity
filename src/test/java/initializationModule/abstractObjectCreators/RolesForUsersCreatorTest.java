package initializationModule.abstractObjectCreators;

import helpers.DatabaseWrapperStubs;
import helpers.JSONMappingStubs;
import helpers.RolesForUsersCreatorStubs;
import org.junit.jupiter.api.Test;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.abstractMappingObjects.RolesForUsers;
import org.safety.library.initializationModule.abstractMappingUsers.RolesForUsersUser;
import org.safety.library.initializationModule.abstractObjectCreators.RolesForUsersCreator;
import org.safety.library.models.Role;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RolesForUsersCreatorTest {
    Map<String, Role> stubbedRoles = DatabaseWrapperStubs.stubbedRoles;

    @Test
    public void createValidRolesUsersFromFactory() {
        //given
        RolesForUsersCreator rolesForUsersCreator = new RolesForUsersCreator(DatabaseWrapperStubs.getDatabaseWrappersMock());
        JSONMapping jsonMapping = JSONMappingStubs.getJSONMappingMock(RolesForUsersCreatorStubs.stubbedFactoryResults);

        //when
        RolesForUsers result = rolesForUsersCreator.createRolesForUsers(jsonMapping);

        //then
        Role role = result.getUsersRoles().get(1);
        System.out.println(role);
        assertTrue(result.getUsersRoles().get(1).equals(new Role("admin")));
        assertTrue(result.getUsersRoles().get(2).equals(new Role("ksiegowy")));
        assertTrue(result.getUsersRoles().get(3).equals(new Role("tester")));
        assertTrue(result.getUsersRoles().get(4).equals(new Role("hacker")));

        assertTrue(result.getUsersRoles().get(1).equals(stubbedRoles.get("admin")));
        assertTrue(result.getUsersRoles().get(2).equals(stubbedRoles.get("ksiegowy")));
        assertTrue(result.getUsersRoles().get(3).equals(stubbedRoles.get("tester")));
        assertTrue(result.getUsersRoles().get(4).equals(stubbedRoles.get("hacker")));
    }
}
