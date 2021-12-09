package org.safety.library;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.models.TestModel;

public class Main {
    public static void main(String[] args) {
        Session session = SessionProvider.getSession();
        Transaction tx = session.beginTransaction();
        TestModel testModel = new TestModel("jakas wartosc");
        session.save(testModel);
        tx.commit();
        session.close();
    }
}
