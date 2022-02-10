package org.safety.library.SQLModule;

import org.safety.library.RolesPrivilegesMap.RolesPrivilegesMap;
import org.safety.library.models.Role;

import java.util.List;

public class QueryMaster {
    private final Builder queryBuilder;

    public QueryMaster(){
        queryBuilder = new DerbyQueryBuilder();
    }

    public String buildQuery(String sql, String tableName, Role role) throws Exception {
        String safeSql = queryBuilder.getModifiedSQL(sql, tableName, role);
        List<String> splitSafeSql = List.of(safeSql.split(" "));
        if(splitSafeSql.get(splitSafeSql.size() - 1).equalsIgnoreCase("where")){
            safeSql = safeSql + " 0 = 1 ";
        }
        return safeSql;
    }
}
