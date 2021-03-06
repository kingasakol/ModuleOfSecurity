package initializationModule.integrationTests;

import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.safety.library.annotations.ACL;
import org.safety.library.initializationModule.Initializer;
import org.safety.library.initializationModule.testEntities.SomeProtectedClass1;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.testEntities.SomeProtectedClass2;
import org.safety.library.initializationModule.testEntities.TestUsers;
import org.safety.library.initializationModule.utils.Authenticator;
import org.safety.library.models.*;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InitializationModuleIntegrationTests {

    Session session = SessionProvider.getSession();
    SomeProtectedClass1 someProtectedClass11;
    SomeProtectedClass1 someProtectedClass12;
    SomeProtectedClass2 someProtectedClass21;
    SomeProtectedClass2 someProtectedClass22;

    @ACL
    public List<SomeProtectedClass1> safelySelectSomeProtectedClass1(){
        return session.createQuery("FROM SomeProtectedClass1 ").getResultList();
    }

    @ACL
    public List<SomeProtectedClass2> safelySelectSomeProtectedClass2(){
        return session.createQuery("FROM SomeProtectedClass2 ").getResultList();
    }

    @ACL
    public void safelyInsert(Object o){
        Transaction tx = session.beginTransaction();
        this.session.save(o);
        tx.commit();
    }

    @ACL
    public void safelyUpdate(Object o){
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
    }

    @ACL
    public void safelyDelete(Object o){
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
    }


    @BeforeAll
    public void prepareForIntegrationTest() throws Exception {
        session.beginTransaction();
        session.createQuery("DELETE FROM TestUsers ").executeUpdate();
        session.createQuery("DELETE FROM SomeProtectedClass1 ").executeUpdate();
        session.createQuery("DELETE FROM SomeProtectedClass2 ").executeUpdate();
        session.getTransaction().commit();
        session.beginTransaction();
        TestUsers testUser1 = new TestUsers((long) 1, "Janek Admin");
        TestUsers testUser2 = new TestUsers((long) 4, "Zosia ksiegowa");
        TestUsers testUser3 = new TestUsers((long) 7, "Kacper tester");
        TestUsers testUser4 = new TestUsers((long) 10, "Anna hacker");
        someProtectedClass11 = new SomeProtectedClass1("wazne dane11", "inne wazne dane", (long) 1);
        someProtectedClass12 = new SomeProtectedClass1("wazne dane12", "inne wazne dane", (long) 2);
        someProtectedClass21 = new SomeProtectedClass2("wazne dane21", "inne wazne dane", (long) 1);
        someProtectedClass22 = new SomeProtectedClass2("wazne dane22", "inne wazne dane", (long) 2);
        this.session.save(testUser1);
        this.session.save(testUser2);
        this.session.save(testUser3);
        this.session.save(testUser4);
        this.session.save(someProtectedClass11);
        this.session.save(someProtectedClass12);
        this.session.save(someProtectedClass21);
        this.session.save(someProtectedClass22);
        session.getTransaction().commit();

        //given
        Session session = SessionProvider.getSession();
        Initializer initializer = new Initializer();

        //when
        initializer.initialize();
    }

    @Test
    public void usesInterceptorIfAnnotatedWithACL(){
        Authenticator.getInstance().setUserId(10); // hacker
        List<String> allData = Arrays.asList("wazne dane12");

        List<SomeProtectedClass1> unprotectedData = safelySelectSomeProtectedClass1();

        Assertions.assertEquals(unprotectedData.stream().map(SomeProtectedClass1::getSomeValue).collect(Collectors.toList()), allData);
    }

    @Test
    public void safelySelectTest() {
        List<SomeProtectedClass1> listRows;

        Authenticator.getInstance().setUserId(1); // admin
        listRows = safelySelectSomeProtectedClass1();
        List<String> adminAccessClass1 = Arrays.asList("wazne dane11", "wazne dane12");
        Assertions.assertEquals(listRows.stream().map(SomeProtectedClass1::getSomeValue).collect(Collectors.toList()), adminAccessClass1);

        Authenticator.getInstance().setUserId(4); // ksiegowy id spad??o z rowerka
        listRows = safelySelectSomeProtectedClass1();
        List<String> ksiegowyAccessClass1 = Arrays.asList("wazne dane11", "wazne dane12");
        Assertions.assertEquals(listRows.stream().map(SomeProtectedClass1::getSomeValue).collect(Collectors.toList()), ksiegowyAccessClass1);

        Authenticator.getInstance().setUserId(10); // hacker
        listRows = safelySelectSomeProtectedClass1();
        List<String> hackerAccessClass1 = Arrays.asList("wazne dane12");
        Assertions.assertEquals(listRows.stream().map(SomeProtectedClass1::getSomeValue).collect(Collectors.toList()), hackerAccessClass1);

        Authenticator.getInstance().setUserId(10); // hacker
        List<SomeProtectedClass2> listRows2 = safelySelectSomeProtectedClass2();
        Assertions.assertEquals(listRows2.size(), 0);
    }

    @Test
    public void safelyInsertTest() {
        Authenticator.getInstance().setUserId(10); // hacker

        SomeProtectedClass1 hackerInsertSomeProtectedClass1 = new SomeProtectedClass1("hacker", "hacker", (long) 3);
        assertDoesNotThrow(() -> safelyInsert(hackerInsertSomeProtectedClass1));

        SomeProtectedClass2 hackerInsertSomeProtectedClass2 = new SomeProtectedClass2("hacker", "hacker", (long) 3);
        Assertions.assertThrows(RuntimeException.class, () -> safelyInsert(hackerInsertSomeProtectedClass2));
    }

    @Test
    public void updateACLAfterInsertTest() {
        Authenticator.getInstance().setUserId(10);
        SomeProtectedClass1 hackerInsertSomeProtectedClass1 = new SomeProtectedClass1("hacker", "hacker", (long) 3);
        safelyInsert(hackerInsertSomeProtectedClass1);

        List<AccessListRow> list = session.createQuery("FROM AccessListRow ").list();

        Assertions.assertEquals(list.get(16).getTableName(), "SomeProtectedClass1".toLowerCase());
        Assertions.assertEquals(list.get(17).getTableName(), "SomeProtectedClass1".toLowerCase());
        Assertions.assertEquals(list.get(18).getTableName(), "SomeProtectedClass1".toLowerCase());
        Assertions.assertEquals(list.get(19).getTableName(), "SomeProtectedClass1".toLowerCase());

        Assertions.assertEquals(list.get(16).getProtectedDataId(), 3);
        Assertions.assertEquals(list.get(17).getProtectedDataId(), 3);
        Assertions.assertEquals(list.get(18).getProtectedDataId(), 3);
        Assertions.assertEquals(list.get(19).getProtectedDataId(), 3);

        Assertions.assertEquals(list.get(16).getRole().getDefaultPriviliges().get(0).getRole().getName(), "admin");
        Assertions.assertEquals(list.get(17).getRole().getDefaultPriviliges().get(0).getRole().getName(), "ksiegowy");
        Assertions.assertEquals(list.get(18).getRole().getDefaultPriviliges().get(0).getRole().getName(), "tester");
        Assertions.assertEquals(list.get(19).getRole().getDefaultPriviliges().get(0).getRole().getName(), "hacker");


        Assertions.assertEquals(list.size(), 20);
    }

    @Test
    @ACL
    public void safelyUpdateTest() {
        // TOSOLVE
        if(someProtectedClass12 == null){
            someProtectedClass12 = (SomeProtectedClass1) session.createQuery("FROM SomeProtectedClass1 S WHERE S.id = "+2).getResultList().get(0);
        }

        Authenticator.getInstance().setUserId(10); // hacker
        Transaction tx = session.beginTransaction();

        someProtectedClass12.setSomeValue("better hacker");
        this.session.update(someProtectedClass12);
        final Transaction finalTx = tx;
        assertDoesNotThrow(() -> finalTx.commit());

        Transaction tx1 = session.beginTransaction();

        Authenticator.getInstance().setUserId(4); // ksiegowy
        someProtectedClass12.setSomeValue("better ksiegowy");
        this.session.update(someProtectedClass12);
        final Transaction finalTx1 = tx;
        Assertions.assertThrows(RuntimeException.class, () -> finalTx1.commit());
    }

    @Test
    public void safelyDeleteTest() {
        Authenticator.getInstance().setUserId(10); // ksiegowy

        List<SomeProtectedClass1> listOfSomeProtectedClass = safelySelectSomeProtectedClass1();
        SomeProtectedClass1 someProtectedClass1 = listOfSomeProtectedClass.get(0);
        assertDoesNotThrow(() -> safelyDelete(someProtectedClass1));

        Authenticator.getInstance().setUserId(4); // ksiegowy

        List<SomeProtectedClass2> listOfSomeProtectedClass2 = safelySelectSomeProtectedClass2();
        SomeProtectedClass2 someProtectedClass2 = listOfSomeProtectedClass2.get(0);
        Assertions.assertThrows(RuntimeException.class, () -> safelyDelete(someProtectedClass2));

    }

    @Test
    @ACL
    public void updateACLAfterDeleteTest() {
        Authenticator.getInstance().setUserId(10); // ksiegowy
        Transaction tx = session.beginTransaction();

        List<SomeProtectedClass1> listOfSomeProtectedClass = session.createQuery("FROM SomeProtectedClass1 ").list();
        SomeProtectedClass1 someProtectedClass1 = listOfSomeProtectedClass.get(0);
        session.delete(someProtectedClass1);

        Transaction finalTx = tx;
        finalTx.commit();


        tx = session.beginTransaction();
        List<AccessListRow> list = session.createQuery("FROM AccessListRow ").list();
        list.forEach(accessListRow -> {
            System.out.println(accessListRow.getId() + " " + accessListRow.getProtectedDataId());
            Assertions.assertFalse(accessListRow.getProtectedDataId() == someProtectedClass1.getId() && accessListRow.getTableName().equalsIgnoreCase("SomeProtectedClass1"));
        });

        Assertions.assertEquals(list.size(), 12);

    }


    @Test
    public void initializationModuleIntegrationTest() throws Exception {
        if(someProtectedClass11 == null){
            someProtectedClass11 = (SomeProtectedClass1) session.createQuery("FROM SomeProtectedClass1 S WHERE S.id = "+1).getResultList().get(0);
        }
        if(someProtectedClass12 == null){
            someProtectedClass12 = (SomeProtectedClass1) session.createQuery("FROM SomeProtectedClass1 S WHERE S.id = "+2).getResultList().get(0);
        }
        if(someProtectedClass21 == null){
            someProtectedClass21 = (SomeProtectedClass2) session.createQuery("FROM SomeProtectedClass2 S WHERE S.id = "+1).getResultList().get(0);
        }
        if(someProtectedClass22 == null){
            someProtectedClass22 = (SomeProtectedClass2) session.createQuery("FROM SomeProtectedClass2 S WHERE S.id = "+2).getResultList().get(0);
        }

        //then
        List<AccessListRow> accessListRows = session.createQuery("FROM AccessListRow ").list();
        List<AddPrivilege> addPrivileges = session.createQuery("FROM AddPrivilege ").list();
        List<Role> roles = session.createQuery("FROM Role ").list();
        List<UsersRole> usersRoles = session.createQuery("FROM UsersRole ").list();
        List<HibernateSelect> hibernateSelects = session.createQuery("FROM HibernateSelect ").list();
        List<DefaultPrivilige> adminDefs = roles.get(0).getDefaultPriviliges();
        List<DefaultPrivilige> ksiegowyDefs = roles.get(1).getDefaultPriviliges();
        List<DefaultPrivilige> testerDefs = roles.get(2).getDefaultPriviliges();
        List<DefaultPrivilige> hackerDefs = roles.get(3).getDefaultPriviliges();


        assertEquals(accessListRows.size(), 16);
        assertEquals(accessListRows.get(0), new AccessListRow(new Role("hacker"), 1, "SomeProtectedClass1", false, false, false));
        assertEquals(accessListRows.get(2), new AccessListRow(new Role("admin"), 1, "SomeProtectedClass1", true, true, true));
        assertEquals(accessListRows.get(3), new AccessListRow(new Role("admin"), 2, "SomeProtectedClass1",true,false,true));
        assertEquals(accessListRows.get(6), new AccessListRow(new Role("ksiegowy"), 1, "SomeProtectedClass1", true, false, false));
        assertEquals(accessListRows.get(10), new AccessListRow(new Role("admin"), 1, "SomeProtectedClass2", true, true, true));
        assertEquals(accessListRows.get(12), new AccessListRow(new Role("tester"), 1, "SomeProtectedClass2", true, false, true));

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
        assertEquals(usersRoles.get(1), new UsersRole(4, new Role("ksiegowy")));
        assertEquals(usersRoles.get(2), new UsersRole(7, new Role("tester")));
        assertEquals(usersRoles.get(3), new UsersRole(10, new Role("hacker")));

        assertEquals(hibernateSelects.size(), 2);
        assertEquals(hibernateSelects.get(0), new HibernateSelect("someprotec0_", "SomeProtectedClass1"));
        assertEquals(hibernateSelects.get(1), new HibernateSelect("someprotec0_", "SomeProtectedClass2"));
        // asserting that Roles have been mapped well
        assertEquals(adminDefs.get(0).getTableName(), "SomeProtectedClass1");
        assertEquals(adminDefs.get(0).isCanUpdate(), true);
        assertEquals(adminDefs.get(0).isCanRead(), true);
        assertEquals(adminDefs.get(0).isCanDelete(), true);

        assertEquals(adminDefs.get(1).getTableName(), "SomeProtectedClass2");
        assertEquals(adminDefs.get(1).isCanUpdate(), true);
        assertEquals(adminDefs.get(1).isCanRead(), true);
        assertEquals(adminDefs.get(1).isCanDelete(), true);

        assertEquals(ksiegowyDefs.get(0).getTableName(), "SomeProtectedClass1");
        assertEquals(ksiegowyDefs.get(0).isCanUpdate(), false);
        assertEquals(ksiegowyDefs.get(0).isCanRead(), true);
        assertEquals(ksiegowyDefs.get(0).isCanDelete(), false);

        assertEquals(ksiegowyDefs.get(1).getTableName(), "SomeProtectedClass2");
        assertEquals(ksiegowyDefs.get(1).isCanUpdate(), false);
        assertEquals(ksiegowyDefs.get(1).isCanRead(), true);
        assertEquals(ksiegowyDefs.get(1).isCanDelete(), false);

        assertEquals(hackerDefs.get(0).getTableName(), "SomeProtectedClass1");
        assertEquals(hackerDefs.get(0).isCanUpdate(), true);
        assertEquals(hackerDefs.get(0).isCanRead(), true);
        assertEquals(hackerDefs.get(0).isCanDelete(), true);

        assertEquals(hackerDefs.get(1).getTableName(), "SomeProtectedClass2");
        assertEquals(hackerDefs.get(1).isCanUpdate(), false);
        assertEquals(hackerDefs.get(1).isCanRead(), false);
        assertEquals(hackerDefs.get(1).isCanDelete(), false);

        assertEquals(testerDefs.get(0).getTableName(), "SomeProtectedClass1");
        assertEquals(testerDefs.get(0).isCanUpdate(), true);
        assertEquals(testerDefs.get(0).isCanRead(), true);
        assertEquals(testerDefs.get(0).isCanDelete(), false);

        assertEquals(testerDefs.get(1).getTableName(), "SomeProtectedClass2");
        assertEquals(testerDefs.get(1).isCanUpdate(), true);
        assertEquals(testerDefs.get(1).isCanRead(), true);
        assertEquals(testerDefs.get(1).isCanDelete(), false);
    }
}
