package org.safety.library.initializationModule.utils;

import org.hibernate.Session;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.Exceptions.RoleForUserNotFoundException;
import org.safety.library.models.AccessListRow;
import org.safety.library.models.AddPrivilege;
import org.safety.library.models.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseWrappers {

    public Map<String, Role> getRolesByItsNames(){
        Session session = SessionProvider.getSession();
        Map<String, Role> result = new HashMap<>();
        List<Role> queriedRoles = session.createQuery("FROM Role R").getResultList();
        queriedRoles.forEach(role -> {
            result.put(role.getName(), role);
        });
        return result;
    }

    public Role getRoleByUserID(Long userId) throws RoleForUserNotFoundException {
        Session session = SessionProvider.getSession();
        List<Role> roles = session.createQuery("FROM UsersRole U WHERE U.userId = "+Long.toString(userId)).list();

        if(roles.size() != 1){
            throw new RoleForUserNotFoundException("There is no Role associated with this userId in a database");
        }
        return roles.get(0);
    }

    public List<AccessListRow> getAccessForRole(Role role) {
        Long id = role.getId();
        Session session = SessionProvider.getSession();
        List<AccessListRow> accessListRow = session.createQuery("FROM AccessListRow AC WHERE AC.role.id = " + Long.toString(id)).list();

        session.close();
        return accessListRow;
    }

    public List<AddPrivilege> getAddPrivilege(Role role) {
        Long id = role.getId();
        Session session = SessionProvider.getSession();
        List<AddPrivilege> addPrivilege = session.createQuery("FROM AddPrivilege AD WHERE AD.role.id = " + Long.toString(id)).list();

        session.close();
        return addPrivilege;
    }
}
