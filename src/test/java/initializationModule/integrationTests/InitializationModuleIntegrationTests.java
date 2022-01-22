package initializationModule.integrationTests;

import org.junit.jupiter.api.BeforeEach;
import org.safety.library.initializationModule.Initializer;
import org.safety.library.initializationModule.testEntities.SomeProtectedClass1;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.testEntities.SomeProtectedClass2;
import org.safety.library.initializationModule.testEntities.TestUsers;
import org.safety.library.models.*;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;


@Transactional
public class InitializationModuleIntegrationTests {

    Session session = SessionProvider.getSession();

    @BeforeEach
    public void prepareForIntegrationTest(){
        session.beginTransaction();
        session.createQuery("DELETE FROM TestUsers ").executeUpdate();
        session.createQuery("DELETE FROM SomeProtectedClass1 ").executeUpdate();
        session.createQuery("DELETE FROM SomeProtectedClass2 ").executeUpdate();
        session.getTransaction().commit();
        session.beginTransaction();
        TestUsers testUser1 = new TestUsers((long)1, "Janek Admin");
        TestUsers testUser2 = new TestUsers((long)2, "Zosia ksiegowa");
        TestUsers testUser3 = new TestUsers((long)3, "Kacper tester");
        TestUsers testUser4 = new TestUsers((long)4, "Anna hacker");
        SomeProtectedClass1 someProtectedClass11 = new SomeProtectedClass1("wazne dane", "inne wazne dane", (long)1);
        SomeProtectedClass1 someProtectedClass12 = new SomeProtectedClass1("wazne dane", "inne wazne dane", (long)2);
        SomeProtectedClass2 someProtectedClass21 = new SomeProtectedClass2("wazne dane", "inne wazne dane", (long)1);
        SomeProtectedClass2 someProtectedClass22 = new SomeProtectedClass2("wazne dane", "inne wazne dane", (long)2);
        this.session.save(testUser1);
        this.session.save(testUser2);
        this.session.save(testUser3);
        this.session.save(testUser4);
        this.session.save(someProtectedClass11);
        this.session.save(someProtectedClass12);
        this.session.save(someProtectedClass21);
        this.session.save(someProtectedClass22);
        session.getTransaction().commit();
    }

    @Test
    public void initializationModuleIntegrationTest() throws Exception {
        //given
        Session session = SessionProvider.getSession();
        Initializer initializer = new Initializer();

        //when
        initializer.initialize();

        //then
        List<AccessListRow> accessListRows = session.createQuery("FROM AccessListRow ").getResultList();
        List<AddPrivilege> addPrivileges = session.createQuery("FROM AddPrivilege ").getResultList();
        List<Role> roles = session.createQuery("FROM Role ").getResultList();
        List<UsersRole> usersRoles = session.createQuery("FROM UsersRole ").getResultList();
        List<HibernateSelect> hibernateSelects = session.createQuery("FROM HibernateSelect ").getResultList();

        assertEquals(accessListRows.size(), 16);
        assertEquals(accessListRows.get(0), new AccessListRow(new Role("hacker"), 1, "SomeProtectedClass1", false, false, false));
        assertEquals(accessListRows.get(2), new AccessListRow(new Role("admin"), 1, "SomeProtectedClass1", true, true, true));
        assertEquals(accessListRows.get(6), new AccessListRow(new Role("ksiegowy"), 1, "SomeProtectedClass1", true, false, false));
        assertEquals(accessListRows.get(10), new AccessListRow(new Role("admin"), 1, "SomeProtectedClass2", true, true, true));
        assertEquals(accessListRows.get(12), new AccessListRow(new Role("tester"), 1, "SomeProtectedClass2", true, true, false));

        assertEquals(roles.size(), 4);
        assertEquals(roles.get(0), new Role("admin"));
        assertEquals(roles.get(1), new Role("ksiegowy"));
        assertEquals(roles.get(2), new Role("tester"));
        assertEquals(roles.get(3), new Role("hacker"));

        assertEquals(addPrivileges.size(), 5);
        assertEquals(addPrivileges.get(0), new AddPrivilege(new Role("hacker"), "SomeProtectedClass1"));
        assertEquals(addPrivileges.get(1), new AddPrivilege(new Role("admin"), "SomeProtectedClass1"));
        assertEquals(addPrivileges.get(2), new AddPrivilege(new Role("admin"), "SomeProtectedClass2"));
        assertEquals(addPrivileges.get(3), new AddPrivilege(new Role("tester"), "SomeProtectedClass2"));
        assertEquals(addPrivileges.get(4), new AddPrivilege(new Role("ksiegowy"), "SomeProtectedClass1"));

        assertEquals(usersRoles.size(), 4);
        assertEquals(usersRoles.get(0), new UsersRole(1, new Role("admin")));
        assertEquals(usersRoles.get(1), new UsersRole(2, new Role("ksiegowy")));
        assertEquals(usersRoles.get(2), new UsersRole(3, new Role("tester")));
        assertEquals(usersRoles.get(3), new UsersRole(4, new Role("hacker")));

        assertEquals(hibernateSelects.size(), 2);
        assertEquals(hibernateSelects.get(0), new HibernateSelect("someprotec0_", "SomeProtectedClass1"));
        assertEquals(hibernateSelects.get(1), new HibernateSelect("someprotec0_", "SomeProtectedClass2"));

    }
}
