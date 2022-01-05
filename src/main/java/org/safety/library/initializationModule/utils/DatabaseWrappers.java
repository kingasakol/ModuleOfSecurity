package org.safety.library.initializationModule.utils;

import org.hibernate.Session;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.models.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseWrappers {

    public Map<String, Role> getRolesByItsNames(){
        Session session = SessionProvider.getSession();
        Map<String, Role> result = new HashMap<>();
        List<Role> queriedRoles = session.createQuery("FROM Role R").list();
        queriedRoles.forEach(role -> {
            result.put(role.getName(), role);
        });
        return result;
    }
}
