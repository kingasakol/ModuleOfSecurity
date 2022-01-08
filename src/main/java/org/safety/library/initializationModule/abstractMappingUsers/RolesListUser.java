package org.safety.library.initializationModule.abstractMappingUsers;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.JSONMappingUser;
import org.safety.library.initializationModule.abstractMappingObjects.RolesList;
import org.safety.library.models.Role;


public class RolesListUser implements JSONMappingUser {
    private final RolesList rolesList;

    public RolesListUser(RolesList rolesList) {
        this.rolesList = rolesList;
    }

    @Override
    public void use() {
        for (Role role : this.rolesList.getRoles()) {
            Session session = SessionProvider.getSession();
            Transaction tx = session.beginTransaction();
            session.save(role);
            tx.commit();
            session.close();
        }
    }
}
