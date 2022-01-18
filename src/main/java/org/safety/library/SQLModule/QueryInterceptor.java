package org.safety.library.SQLModule;


import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.safety.library.RolesPrivilegesMap.RolesPrivilegesMap;
import java.io.Serializable;

public class QueryInterceptor extends EmptyInterceptor {
    @Override
    public String onPrepareStatement(String sql) {
        //TODO
        String preparedSQL = sql;
        System.out.println(preparedSQL);
        return super.onPrepareStatement(preparedSQL);
    }


    // Used if something is deleted from Database to update Access List
    // needed id (second argument) and entity.getClass().getSimpleName() for UpdateACLCLass
    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        //TODO
        super.onDelete(entity, id, state, propertyNames, types);
    }

    // Used if something is added to the Database to update Access List
    // needed id (second argument) and entity.getClass().getSimpleName() for UpdateACLCLass
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        //TODO
        return super.onSave(entity, id, state, propertyNames, types);
    }

    public RolesPrivilegesMap filterRolesPrivilegesMap(RolesPrivilegesMap rolesPrivilegesMap){
        //TODO
        return null;
    }
}
