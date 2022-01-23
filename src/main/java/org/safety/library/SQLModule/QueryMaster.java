package org.safety.library.SQLModule;

import org.safety.library.RolesPrivilegesMap.RolesPrivilegesMap;

public class QueryMaster {
    private final Builder queryBuilder;

    public QueryMaster(){
        queryBuilder = new DerbyQueryBuilder();
    }

    public String buildQuery(String sql, RolesPrivilegesMap rolesPrivilegesMap) throws Exception {
        return queryBuilder.returnPreparedSQL(rolesPrivilegesMap.getFilteredList(), sql);
    }
}
