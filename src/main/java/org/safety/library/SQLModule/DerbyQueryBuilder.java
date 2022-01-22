package org.safety.library.SQLModule;

import org.safety.library.models.AccessListRow;

import java.util.List;
import java.util.Locale;

public class DerbyQueryBuilder implements Builder{

    //example sql query
    private String exampleSQL = "select testmodel0_.testModelID as testmode1_2_, testmodel0_.someValue as somevalu2_2_ from test_model testmodel0_";

    private String[] splitQueryOnSpaces(String sql){
        return sql.split(" ");
    }

    private static String removeSuffix(final String s, final String suffix) {
        if (s != null && suffix != null && s.endsWith(suffix)) {
            return s.substring(0, s.length() - suffix.length());
        }
        return s;
    }

    @Override
    public boolean alreadyHasWhereClause(String sql) {
        for(String word : splitQueryOnSpaces(sql)){
            if(word.toLowerCase(Locale.ROOT).equals("where")){
                return true;
            }
        }
        return false;
    }

    @Override
    public String[] splitStringOnAdditionPlace(String sql) {
        for(String word: splitQueryOnSpaces(sql)){
            if(word.toLowerCase(Locale.ROOT).equals("order")){
                String[] result = sql.split("order");
                result[1] = " order "+result[1];
                return  result;
            }
            else if(word.toLowerCase(Locale.ROOT).equals("where")){
                String[] result = sql.split("where");
                result[0] = result[0]+" where ";
                return result;
            }
        }
       return new String[]{sql, ""};
    }

    @Override
    public String prepareSQLAddition(List<AccessListRow> accessListRows, String sql) {
        String result = "";
        for(AccessListRow accessListRow: accessListRows){
            result += " "+accessListRow.getTableName()+"."+"id = "+accessListRow.getProtectedDataId()+" and ";
        }
        if(!alreadyHasWhereClause(sql)){
            result = " where "+result;
            result = DerbyQueryBuilder.removeSuffix(result, "and ");
        }
        return result;
    }

    @Override
    public String returnPreparedSQL(List<AccessListRow> accessListRows, String sql) {
        String[] splitedSql = splitStringOnAdditionPlace(sql);
        return splitedSql[0]+prepareSQLAddition(accessListRows, sql)+splitedSql[1];
    }
}
