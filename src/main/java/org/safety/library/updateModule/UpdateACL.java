package org.safety.library.updateModule;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.models.AccessListRow;
import org.safety.library.models.DefaultPrivilige;
import org.safety.library.models.Role;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class UpdateACL {


    public static void updateAfterDelete(String tableName, Long id) {
        Session session = SessionProvider.getSessionWithoutInterceptor();
        session.beginTransaction();

        List<AccessListRow> list = session.createQuery("FROM AccessListRow ").list();
        AtomicLong idInACL = new AtomicLong();
        list.forEach(accessListRow -> {
            if(accessListRow.getProtectedDataId() == id && accessListRow.getTableName().equalsIgnoreCase(tableName)) {
                idInACL.set(accessListRow.getId());
                session.createQuery("DELETE FROM AccessListRow aLR WHERE aLR.id=" + idInACL).executeUpdate();
            }
        });
        session.getTransaction().commit();
    }

    private static Session session = SessionProvider.getSessionWithoutInterceptor();

    public static void updateAfterInsert(String tableName, int id) {
        Transaction tx = session.getTransaction();
        session = SessionProvider.getSessionWithoutInterceptor();
        session.beginTransaction();
        List<Role> roles = session.createQuery("FROM Role ").list();
        roles.forEach(role -> {
            List<DefaultPrivilige> priviliges = role.getDefaultPriviliges();
            priviliges.forEach(privillage -> {
                String table = privillage.getTableName();
                if(table.equalsIgnoreCase(tableName)) {
                    AccessListRow newAccessListRow = new AccessListRow(role, id, tableName, privillage.isCanRead(), privillage.isCanDelete(), privillage.isCanUpdate());
                    session.save(newAccessListRow);
                }
            });
        });

        if (session.getTransaction().getStatus().equals(TransactionStatus.ACTIVE)) {
            session.getTransaction().commit();
        }

    }
}
