package org.safety.library.SQLModule;


import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.type.Type;

import org.safety.library.RolesPrivilegesMap.RolesPrivilegesMap;
import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.utils.Authenticator;
import org.safety.library.initializationModule.utils.DatabaseWrappers;
import org.safety.library.models.AccessListRow;
import org.safety.library.models.HibernateSelect;
import org.safety.library.models.Role;
import org.safety.library.updateModule.UpdateACL;
import org.safety.library.annotations.ACL;


import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class QueryInterceptor extends EmptyInterceptor {

    private final DatabaseWrappers databaseWrappers = new DatabaseWrappers();
    private List<String> protectedTables = new LinkedList<>();
    private final List<String> goTrough = new LinkedList<>(Arrays.asList(
            "HibernateSelect".toLowerCase(),
            "AccessList".toLowerCase(),
            "Default_Privilige".toLowerCase(),
            "Role_default_privilige".toLowerCase(),
            "Role".toLowerCase(),
            "UsersRole".toLowerCase(),
            "AddPrivilege".toLowerCase()
    ));
    private static long magickId;
    private int idForInsert;


    private List<String> getProtectedTables() {
        Session session = SessionProvider.getSessionWithoutInterceptor();
        List<HibernateSelect> hibernateSelects = session.createQuery("FROM HibernateSelect ").list();
        return hibernateSelects.stream()
                .map(HibernateSelect::getEntityName)
                .toList();
    }

    private boolean isInvokedFromACLMethod() {
        StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
        for(StackTraceElement traceElement: traceElements){
            try {
                Class traceElementClass = Class.forName(traceElement.getClassName());
                for(Method m: traceElementClass.getMethods()){
                    if(m.getName().equals(traceElement.getMethodName()) && m.isAnnotationPresent(ACL.class)){
                        return true;
                    }
                }
            } catch (ClassNotFoundException e) {}
        }
        return false;
    }

    @Override
    public String onPrepareStatement(String sql) {
        if(!this.isInvokedFromACLMethod()){
            return sql;
        }

        String tableName = QueryProcessor.getUsedTable(sql);
        if (goTrough.contains(tableName)) {
            return super.onPrepareStatement(sql);
        }

        protectedTables = getProtectedTables();

        if (!protectedTables.contains(tableName)) {
            return super.onPrepareStatement(sql);
        }

        QueryType type = QueryProcessor.getQueryType(sql);

        switch (type) {
            case SELECT -> {
                QueryMaster master = new QueryMaster();
                try {
                    return master.buildQuery(sql, QueryProcessor.getUsedTable(sql), Authenticator.getInstance().getRole());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case INSERT -> {
                RolesPrivilegesMap privilegesMap = new RolesPrivilegesMap(databaseWrappers, tableName, type);
                if (!privilegesMap.canCreate()) {
                    throw new RuntimeException("Insert Denied");
                }
                UpdateACL.updateAfterInsert(tableName, idForInsert);
            }
            case UPDATE -> {
                RolesPrivilegesMap privilegesMap = new RolesPrivilegesMap(databaseWrappers, tableName, type);
                AccessListRow accessListRow = privilegesMap.getRowPrivilegesById(magickId);
                if (!accessListRow.isCanUpdate()) {
                    throw new RuntimeException("Update Denied");
                }
            }
            case DELETE -> {
                RolesPrivilegesMap privilegesMap = new RolesPrivilegesMap(databaseWrappers, tableName, type);
                AccessListRow accessListRow = privilegesMap.getRowPrivilegesById(magickId);
                if (!accessListRow.isCanDelete()) {
                    throw new RuntimeException("Delete Denied");
                }
                UpdateACL.updateAfterDelete(tableName, magickId);
            }
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
        idForInsert = Integer.parseInt(id.toString());
        return super.onSave(entity, id, state, propertyNames, types);
    }

    // Used if something is deleted from Database to update Access List
    // needed id (second argument) and entity.getClass().getSimpleName() for UpdateACLCLass
    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        magickId = (long) id;
        super.onDelete(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        magickId = (long) id;
        return false;
    }
}
