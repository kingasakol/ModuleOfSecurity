package org.safety.library.SQLModule;


import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.safety.library.RolesPrivilegesMap.RolesPrivilegesMap;
import org.safety.library.initializationModule.utils.DatabaseWrappers;
import org.safety.library.models.Role;

import java.io.Serializable;

public class QueryInterceptor extends EmptyInterceptor {

    @Override
    public String onPrepareStatement(String sql) {

        String preparedSQL = sql;
        System.out.println("Interceptor onPrepareStatement");
        System.out.println(preparedSQL);
        System.out.println("used table " + QueryProcessor.getUsedTable(preparedSQL));

        if(QueryProcessor.getQueryType((preparedSQL)).equals(QueryType.SELECT)){
            return "select testmodel0_.testModelID as testmode1_2_, testmodel0_.someValue as somevalu2_2_ from test_model testmodel0_ where testmodel0_.testModelID = 5";
        }

        //TODO
        // We do not have initialized module
        // RolesPrivilegesMap rolesPrivilegesMap = new RolesPrivilegesMap(QueryProcessor.getUsedTable(preparedSQL));
        // then filter them and push data to QueryBuilder
        return super.onPrepareStatement(preparedSQL);
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

    public RolesPrivilegesMap filterRolesPrivilegesMap(RolesPrivilegesMap rolesPrivilegesMap) {
        //TODO
        return null;
    }
}
