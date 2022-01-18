package org.safety.library.SQLModule;

public interface Builder {
    public boolean alreadyHasWhereClause();

    /**
     * This method splits SQL String into two parts.
     * The first one is before and second one after the place,
     * where some additional instructions should be inserted.
     * Example: SQL String: "SELECT * FROM Test AS T WHERE T.some = 'foo' ORDER BY T.some"
     * should be splitted onto "SELECT * FROM TEST AS T WHERE T.some ='foo'" and " ORDER BY T.some",
     * because between these parts there should be inserted something like " AND T.id = 1 AND T.id = 4 AND..."
     * taken from privileges
     */
    public String[] splitStringOnAdditionPlace();

    /**
     * Addition to the SQL prepared basing on the roles' privileges.
     * Example: If role has access to the rows with id [1, 4, 5],  there should be prepared this kind
     * of addition: "WHERE table.id = 1 AND table.id = 4 AND table.id = 5"
     */
    public String prepareSQLAddition();

    public String returnPreparedSQL();
}
