package org.safety.library.SQLModule;

import org.safety.library.RolesPrivilegesMap.RolesPrivilegesMap;

import java.util.List;

public class QueryMaster {
    private final Builder queryBuilder;

    public QueryMaster(){
        queryBuilder = new DerbyQueryBuilder();
    }

    public String buildQuery(String sql, RolesPrivilegesMap rolesPrivilegesMap) throws Exception {
        String safeSql = queryBuilder.returnPreparedSQL(rolesPrivilegesMap.getFilteredList(), sql);
        List<String> splitSafeSql = List.of(safeSql.split(" "));
        if(splitSafeSql.get(splitSafeSql.size() - 1).equalsIgnoreCase("where")){
            safeSql = safeSql + " 0 = 1 ";
        }
        return safeSql;
    }
}
