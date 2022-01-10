package org.safety.library.initializationModule.abstractMappingUsers;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.JSONMappingUser;
import org.safety.library.initializationModule.abstractMappingObjects.DataAccess;
import org.safety.library.models.AccessListRow;

import java.util.List;

public class DataAccessUser implements JSONMappingUser {
    private List<DataAccess> dataAccesses;

    public DataAccessUser(List<DataAccess> dataAccesses) {
        this.dataAccesses = dataAccesses;
    }

    @Override
    public void use() {
        this.dataAccesses.forEach(dataAccess -> {
            dataAccess.getAccessForEntity().forEach(((role, permissions) -> {
                permissions.forEach(permission -> {
                    Session session = SessionProvider.getSession();
                    Transaction tx = session.beginTransaction();
                    AccessListRow accessListRow = new AccessListRow(role, permission.getDataId(), dataAccess.getClassType(), permission.isCanRead(), permission.isCanUpdate(), permission.isCanDelete());
                    session.save(accessListRow);
                    tx.commit();
                    session.close();
                });
            }));


        });
    }
}
