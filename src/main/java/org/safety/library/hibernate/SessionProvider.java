package org.safety.library.hibernate;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.safety.library.SQLModule.QueryInterceptor;

public class SessionProvider {
    private static SessionFactory sessionFactory = null;
    private static EmptyInterceptor interceptor = new QueryInterceptor();
    private static Session session;
    private static Session sessionWithoutInterceptor;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            sessionFactory =
                    configuration.configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public static Session getSession(){
        if(session == null || !session.isOpen()){
            session = getSessionFactory().withOptions().interceptor(interceptor).openSession();
        }
        return session;
    }

    public static Session getSessionWithoutInterceptor() {
        if(sessionWithoutInterceptor == null || !sessionWithoutInterceptor.isOpen()){
            sessionWithoutInterceptor = getSessionFactory().withOptions().openSession();
        }
        return sessionWithoutInterceptor;
    }
}
