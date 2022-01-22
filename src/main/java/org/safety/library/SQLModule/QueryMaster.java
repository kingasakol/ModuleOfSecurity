package org.safety.library.SQLModule;

import org.safety.library.RolesPrivilegesMap.RolesPrivilegesMap;

public class QueryMaster {
    private String sql;

    public QueryMaster(String sql){
        this.sql = sql;
    }

    public String buildQuery(RolesPrivilegesMap rolesPrivilegesMap, Builder builder) throws Exception {
        return builder.returnPreparedSQL(rolesPrivilegesMap.getPrivileges(), this.sql);
    }
}
