package org.safety.library.SQLModule;

import org.safety.library.models.AccessListRow;
import org.safety.library.models.Role;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface Builder {
    boolean alreadyHasWhereClause(String sql);

    /**
     * This method splits SQL String into two parts.
     * The first one is before and second one after the place,
     * where some additional instructions should be inserted.
     * Example: SQL String: "SELECT * FROM Test AS T WHERE T.some = 'foo' ORDER BY T.some"
     * should be splitted onto "SELECT * FROM TEST AS T WHERE T.some ='foo'" and " ORDER BY T.some",
     * because between these parts there should be inserted something like " AND T.id = 1 AND T.id = 4 AND..."
     * taken from privileges
     */
    String addACLJoin(String sql);

    String addACLCondition(String sql, String tableName, Role role) throws Exception;

    String getModifiedSQL(String sql, String tableName, Role role) throws Exception;
}
