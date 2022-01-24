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
import org.safety.library.models.Role;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class QueryInterceptor extends EmptyInterceptor {
    private final DatabaseWrappers databaseWrappers = new DatabaseWrappers();

    @Override
    public String onPrepareStatement(String sql) {
        String tableName = QueryProcessor.getUsedTable(sql).toLowerCase();

        List<String> goTrough = new LinkedList<>();
        goTrough.add("HibernateSelect".toLowerCase());
        goTrough.add("AccessList".toLowerCase());
        goTrough.add("default_privilige".toLowerCase());
        goTrough.add("role_default_privilige".toLowerCase());
        goTrough.add("Role".toLowerCase());

        if(goTrough.contains(tableName)){
            return super.onPrepareStatement(sql);
        }

        Session session = SessionProvider.getSession();
        List<HibernateSelect> hibernateSelects = session.createQuery("FROM HibernateSelect ").list();
        List<String> protectedTables = hibernateSelects.stream()
                .map(HibernateSelect::getEntityName)
                .map(String::toLowerCase)
                .toList();

        if(protectedTables.size() == 0){
            return super.onPrepareStatement(sql);
        }

        if(! protectedTables.contains(tableName)){
            return super.onPrepareStatement(sql);
        }

        RolesPrivilegesMap privilegesMap = new RolesPrivilegesMap(databaseWrappers, tableName);

        try {
            QueryType type = QueryProcessor.getQueryType(sql);
            switch (type) {
                // Should work
                case SELECT -> {
                     QueryMaster master = new QueryMaster();
                     return master.buildQuery(sql, privilegesMap);
                }
                case INSERT -> {
                    if(!privilegesMap.canCreate()){
                        throw new AccessDeniedException("Insert Denied");
                    }
                }
                //TODO not working bc QueryProcessor.getId return wrong id or not return at all
                case UPDATE -> {
//                    AccessListRow accessListRow = privilegesMap.getRowPrivileges(QueryProcessor.getId(sql));
//                    if(!accessListRow.isCanUpdate()){
//                        throw new AccessDeniedException("Update Denied");
//                    }
                }
                case DELETE -> {
//                    AccessListRow accessListRow = privilegesMap.getRowPrivileges(QueryProcessor.getId(sql));
//                    if(!accessListRow.isCanDelete()){
//                        throw new AccessDeniedException("Delete Denied");
//                    }
                }
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return super.onPrepareStatement(sql);
    }

    // OnDelete and onSave run before onPrepareStatement !!!

    // Used if something is deleted from Database to update Access List
    // needed id (second argument) and entity.getClass().getSimpleName() for UpdateACLCLass
    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        //TODO
        System.out.println("Interceptor onDelete");
        System.out.println(entity.toString() + ' ' + id + ' ' + entity.getClass().getSimpleName());
        super.onDelete(entity, id, state, propertyNames, types);
    }

    // Used if something is added to the Database to update Access List
    // needed id (second argument) and entity.getClass().getSimpleName() for UpdateACLCLass
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        //TODO
        System.out.println("Interceptor onSave");
        System.out.println(entity.toString() + ' ' + id + ' ' + entity.getClass().getSimpleName());
        return super.onSave(entity, id, state, propertyNames, types);
    }

}
