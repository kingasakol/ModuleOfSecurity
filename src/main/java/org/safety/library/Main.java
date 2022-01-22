package org.safety.library;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.Initializer;
import org.safety.library.models.TestModel;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Session session = SessionProvider.getSession();
        Transaction tx = session.beginTransaction();

        TestModel testModel = new TestModel("jakas wartosc");
        session.save(testModel);

        testModel.setSomeValue("inna wartosc");
        session.update(testModel);

        List<TestModel> testModelList = session.createQuery("FROM TestModel ").getResultList();
        testModelList.forEach(testModel1 -> System.out.println(testModel1.toString()));

        // session.delete(testModel);

        tx.commit();
        session.close();
        System.out.println("DONE DONE DONE DONE DONE DONE!");
    }
}
