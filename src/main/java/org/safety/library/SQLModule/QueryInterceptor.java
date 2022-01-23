package org.safety.library.SQLModule;


import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.safety.library.RolesPrivilegesMap.RolesPrivilegesMap;
import org.safety.library.initializationModule.Exceptions.AccessDeniedException;


import java.io.Serializable;

public class QueryInterceptor extends EmptyInterceptor {

    @Override
    public String onPrepareStatement(String sql) {
        RolesPrivilegesMap privilegesMap = new RolesPrivilegesMap(QueryProcessor.getUsedTable(sql));

        try {
            QueryType type = QueryProcessor.getQueryType(sql);
            switch (type) {
                case SELECT -> {
                     QueryMaster master = new QueryMaster();
                     return master.buildQuery(sql, privilegesMap);
                }
                case INSERT -> {
                    if(!privilegesMap.canCreate()){
                        throw new AccessDeniedException("Insert Denied");
                    }
                    break;
                }
                case UPDATE -> {
                    break; //TODO
                }
                case DELETE -> {
                    break; //TODO
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
