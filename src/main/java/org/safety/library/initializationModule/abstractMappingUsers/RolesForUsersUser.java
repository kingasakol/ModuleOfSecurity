package org.safety.library.initializationModule.abstractMappingUsers;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.JSONMappingUser;
import org.safety.library.initializationModule.abstractMappingObjects.RolesForUsers;
import org.safety.library.models.UsersRole;

import java.util.List;


public class RolesForUsersUser implements JSONMappingUser {
    private RolesForUsers rolesForUsers;

    public RolesForUsersUser(RolesForUsers rolesForUsers) {
        this.rolesForUsers = rolesForUsers;
    }

    @Override
    public void use() {
        rolesForUsers.getUsersRoles().forEach((id, role) -> {
            Session session = SessionProvider.getSession();
            Transaction tx = session.beginTransaction();
            UsersRole usersRole = new UsersRole(id, role);
            session.save(usersRole);
            tx.commit();
            session.close();
        });

    }
}
