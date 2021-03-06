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
            if(dataAccess != null){
                dataAccess.getAccessForEntity().forEach(((role, permissions) -> {
                    permissions.forEach(permission -> {
                        AccessListRow accessListRow = new AccessListRow(role, permission.getDataId(), dataAccess.getClassType(), permission.isCanRead(), permission.isCanDelete(), permission.isCanUpdate());
                        session.save(accessListRow);
                    });
                }));
                String type = dataAccess.getClassType();
                if(type.length() > 10){
                    type = type.substring(0, 10);
                }
                HibernateSelect hibernateSelect = new HibernateSelect(type.toLowerCase(Locale.ROOT)+"0_", dataAccess.getClassType());
                session.save(hibernateSelect);
            }
        });
        tx.commit();
    }
}
