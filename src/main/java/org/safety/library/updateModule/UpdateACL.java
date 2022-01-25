package org.safety.library.updateModule;

import org.hibernate.Session;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.models.AccessListRow;
import org.safety.library.models.DefaultPrivilige;
import org.safety.library.models.Role;

import java.util.List;

public class UpdateACL {


    public static void updateAfterDelete(String tableName, Long id) {
        Session session = SessionProvider.getSession();
        session.createQuery("DELETE FROM AccessListRow aLR WHERE aLR.protectedDataId=" + id +
                " AND aLR.tableName=" + tableName)
                .executeUpdate();
        session.getTransaction().commit();
    }

    public static void updateAfterInsert(String tableName, Long id) {
        Session session = SessionProvider.getSession();
        List<Role> roles = session.createQuery("FROM Role ").list();
        roles.forEach(role -> {
            List<DefaultPrivilige> priviliges = role.getDefaultPriviliges();
            priviliges.forEach(privillage -> {
                String table = privillage.getTableName();
                if(table.equals(tableName)) {
                    AccessListRow newAccessListRow = new AccessListRow(role, id.intValue(), tableName, privillage.isCanRead(), privillage.isCanDelete(), privillage.isCanUpdate());
                    session.save(newAccessListRow);
                    session.getTransaction().commit();
                }
            });
        });
    }
}
