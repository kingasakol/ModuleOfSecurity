package org.safety.library.SQLModule;

public class DerbyQueryBuilder implements Builder{
    @Override
    public boolean alreadyHasWhereClause() {
        return false;
    }

    @Override
    public String[] splitStringOnAdditionPlace() {
        return new String[0];
    }

    @Override
    public String prepareSQLAddition() {
        return null;
    }

    @Override
    public String returnPreparedSQL() {
        return null;
    }
}
