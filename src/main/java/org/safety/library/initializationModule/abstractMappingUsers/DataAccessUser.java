package org.safety.library.initializationModule.abstractMappingUsers;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.JSONMappingUser;
import org.safety.library.initializationModule.abstractMappingObjects.DataAccess;
import org.safety.library.models.AccessListRow;
import org.safety.library.models.HibernateSelect;

import java.util.List;
import java.util.Locale;

public class DataAccessUser implements JSONMappingUser {
    private List<DataAccess> dataAccesses;

    public DataAccessUser(List<DataAccess> dataAccesses) {
        this.dataAccesses = dataAccesses;
    }

    @Override
    public void use() {
        Session session = SessionProvider.getSession();
        Transaction tx = session.beginTransaction();
        this.dataAccesses.forEach(dataAccess -> {
            dataAccess.getAccessForEntity().forEach(((role, permissions) -> {
                permissions.forEach(permission -> {
                    AccessListRow accessListRow = new AccessListRow(role, permission.getDataId(), dataAccess.getClassType(), permission.isCanRead(), permission.isCanUpdate(), permission.isCanDelete());
                    session.save(accessListRow);
                });
            }));
            HibernateSelect hibernateSelect = new HibernateSelect(dataAccess.getClassType().toLowerCase(Locale.ROOT)+"0_", dataAccess.getClassType());
            session.save(hibernateSelect);
        });
        tx.commit();
        session.close();
    }
}
