package org.safety.library.SQLModule;


import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.type.Type;

import org.safety.library.RolesPrivilegesMap.RolesPrivilegesMap;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.Exceptions.AccessDeniedException;
import org.safety.library.initializationModule.utils.DatabaseWrappers;
import org.safety.library.models.AccessListRow;
import org.safety.library.models.HibernateSelect;


import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class QueryInterceptor extends EmptyInterceptor {

    private final DatabaseWrappers databaseWrappers = new DatabaseWrappers();
    List<String> goTrough = new LinkedList<>(Arrays.asList(
            "HibernateSelect".toLowerCase(),
            "AccessList".toLowerCase(),
            "Default_Privilige".toLowerCase(),
            "Role_default_privilige".toLowerCase(),
            "Role".toLowerCase(),
            "UsersRole".toLowerCase()
    ));
    private long magickId;


    private List<String> getProtectedTables() {
        Session session = SessionProvider.getSession();
        List<HibernateSelect> hibernateSelects = session.createQuery("FROM HibernateSelect ").list();
        return hibernateSelects.stream()
                .map(HibernateSelect::getEntityName)
                .map(String::toLowerCase)
                .toList();
    }

    @Override
    public String onPrepareStatement(String sql) {
        String tableName = QueryProcessor.getUsedTable(sql).toLowerCase();
        System.out.println(sql + " SRAJ "  +tableName);
        if (goTrough.contains(tableName)) {
            return super.onPrepareStatement(sql);
        }

        List<String> protectedTables = getProtectedTables();
        if (!protectedTables.contains(tableName)) {
            System.out.println("NIE SRAJ + " + protectedTables);
            return super.onPrepareStatement(sql);
        }

        QueryType type = QueryProcessor.getQueryType(sql);
        RolesPrivilegesMap privilegesMap = new RolesPrivilegesMap(databaseWrappers, tableName);
        try {
            switch (type) {
                case SELECT -> {
                    System.out.println("SELECT SRAJ");
                    QueryMaster master = new QueryMaster();
                    System.out.println(privilegesMap);
                    System.out.println(master.buildQuery(sql, privilegesMap));
                    return master.buildQuery(sql, privilegesMap);
                }
                case INSERT -> {
                    if (!privilegesMap.canCreate()) {
                        throw new AccessDeniedException("Insert Denied");
                    }
                }
                case UPDATE -> {
                    AccessListRow accessListRow = privilegesMap.getRowPrivilegesById(magickId);
                    if (!accessListRow.isCanUpdate()) {
                        throw new AccessDeniedException("Update Denied");
                    }
                }
                case DELETE -> {
                    AccessListRow accessListRow = privilegesMap.getRowPrivilegesById(magickId);
                    if (!accessListRow.isCanDelete()) {
                        throw new AccessDeniedException("Delete Denied");
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return super.onPrepareStatement(sql);
    }

    // OnDelete and onSave run before onPrepareStatement !!!

    // Used if something is added to the Database to update Access List
    // needed id (second argument) and entity.getClass().getSimpleName() for UpdateACLCLass
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        //TODO
//        System.out.println("Interceptor onSave");
//        System.out.println(entity.toString() + ' ' + id + ' ' + entity.getClass().getSimpleName());
        return super.onSave(entity, id, state, propertyNames, types);
    }

    // Used if something is deleted from Database to update Access List
    // needed id (second argument) and entity.getClass().getSimpleName() for UpdateACLCLass
    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
//        System.out.println("Interceptor onDelete");
//        System.out.println(entity.toString() + ' ' + id + ' ' + entity.getClass().getSimpleName());
        magickId = (long) id;
        super.onDelete(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        // just like "onUpdate" S H O U L D W O R K
        System.out.println("Interceptor onUpdate");
        System.out.println(entity.toString() + ' ' + id + ' ' + entity.getClass().getSimpleName());
        magickId = (long) id;
        return true;
    }

}
