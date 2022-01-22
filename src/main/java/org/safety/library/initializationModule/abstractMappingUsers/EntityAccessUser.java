package org.safety.library.initializationModule.abstractMappingUsers;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.JSONMappingUser;
import org.safety.library.initializationModule.abstractMappingObjects.EntityAccess;
import org.safety.library.models.AccessListRow;
import org.safety.library.models.AddPrivilege;

public class EntityAccessUser implements JSONMappingUser {

    private EntityAccess entityAccess;

    public EntityAccessUser(EntityAccess entityAccess){
        this.entityAccess = entityAccess;
    }

    @Override
    public void use() {
        this.entityAccess.getAddPriviledges().forEach(((role, tableNames) -> {
            tableNames.forEach(tableName -> {
                Session session = SessionProvider.getSession();
                Transaction tx = session.beginTransaction();
                AddPrivilege addPrivilege = new AddPrivilege(role, tableName);
                session.save(addPrivilege);
                tx.commit();
            });
        }));
    }
}
