package org.safety.library.SQLModule;

public class QueryProcessor {

    public static QueryType getQueryType(String sql) {
        String stringQueryType = sql.split(" ")[0];

        return switch (stringQueryType.toLowerCase()) {
            case "insert" -> QueryType.INSERT;
            case "update" -> QueryType.UPDATE;
            case "select" -> QueryType.SELECT;
            case "delete" -> QueryType.DELETE;
            default -> throw new IllegalArgumentException();
        };
    }

    private static String getTableName(String sql, String keyWord) {
        String[] stringList = sql.split(" ");
        for (int i = 0; i < stringList.length; i++) {
            if (stringList[i].equalsIgnoreCase(keyWord) && (i + 1) < stringList.length) {
                return stringList[i + 1];
            }
        }
        throw new IllegalArgumentException();
    }

    public static String getUsedTable(String sql) {
        QueryType queryType = QueryProcessor.getQueryType(sql);
        return switch (queryType) {
            case INSERT -> QueryProcessor.getTableName(sql, "into");
            case UPDATE -> QueryProcessor.getTableName(sql, "update");
            case SELECT, DELETE -> QueryProcessor.getTableName(sql, "from");
        };
    }

}
