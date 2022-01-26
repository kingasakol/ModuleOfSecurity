package org.safety.library;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.models.TestModel;

import java.util.List;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        List<TestModel> testModelList;

        Session session = SessionProvider.getSession();
        Transaction tx = session.beginTransaction();

        TestModel testModel1 = new TestModel("wartosc_1");
        session.save(testModel1);

        TestModel testModel2 = new TestModel("wartosc_2");
        session.save(testModel2);

        testModelList = session.createQuery("FROM TestModel ").list();
        testModelList.forEach(_testModel -> System.out.println(_testModel.getSomeValue()));

        testModel1.setSomeValue("wartosc_1_nowa");
        session.update(testModel1);


        testModelList = session.createQuery("FROM TestModel ").list();
        testModelList.forEach(_testModel -> System.out.println(_testModel.getSomeValue()));

        session.delete(testModel2);

        tx.commit();
        System.out.println("DONE DONE DONE DONE DONE DONE!");
    }
}
