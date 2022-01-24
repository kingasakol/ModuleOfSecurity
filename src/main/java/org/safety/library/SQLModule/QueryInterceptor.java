package org.safety.library.SQLModule;


import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.type.Type;

import org.safety.library.hibernate.SessionProvider;
import org.safety.library.initializationModule.utils.DatabaseWrappers;
import org.safety.library.models.HibernateSelect;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class QueryInterceptor extends EmptyInterceptor {
    private final DatabaseWrappers databaseWrappers = new DatabaseWrappers();

    @Override
    public String onPrepareStatement(String sql) {
        String tableName = QueryProcessor.getUsedTable(sql).toLowerCase();
        System.out.println(tableName + " XDDD " + sql);


        List<String> goTrough = new LinkedList<>();
        goTrough.add("HibernateSelect".toLowerCase());
        goTrough.add("AccessList".toLowerCase());

        if(goTrough.contains(tableName)){
            return super.onPrepareStatement(sql);
        }

        Session session = SessionProvider.getSession();
        List<HibernateSelect> hibernateSelects = session.createQuery("FROM HibernateSelect ").list();
        List<String> protectedTables = hibernateSelects.stream()
                .map(HibernateSelect::getEntityName)
                .map(String::toLowerCase)
                .toList();
//
//        if(protectedTables.size() == 0){
//            return super.onPrepareStatement(sql);
//        }
//
//        if(! protectedTables.contains(tableName)){
//            return super.onPrepareStatement(sql);
//        }


//        System.out.println("Lecimy SQL " + tableName);
//        System.out.println(sql);
//        RolesPrivilegesMap privilegesMap = new RolesPrivilegesMap(databaseWrappers, tableName);

//        try {
//            QueryType type = QueryProcessor.getQueryType(sql);
//            switch (type) {
//                case SELECT -> {
//                     QueryMaster master = new QueryMaster();
//                     return master.buildQuery(sql, privilegesMap);
//                }
//                case INSERT -> {
//                    if(!privilegesMap.canCreate()){
//                        throw new AccessDeniedException("Insert Denied");
//                    }
//                    break;
//                }
//                case UPDATE -> {
//                    break; //TODO
//                }
//                case DELETE -> {
//                    break; //TODO
//                }
//            }
//        } catch (Exception exception){
//            exception.printStackTrace();
//        }

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
