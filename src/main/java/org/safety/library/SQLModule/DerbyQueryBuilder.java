package org.safety.library.SQLModule;

import org.safety.library.initializationModule.utils.ClassFinder;
import org.safety.library.models.AccessListRow;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

public class DerbyQueryBuilder implements Builder{

    private String[] splitQueryOnSpaces(String sql){
        return sql.split(" ");
    }

    private static String removeSuffix(final String s, final String suffix) {
        if (s != null && suffix != null && s.endsWith(suffix)) {
            return s.substring(0, s.length() - suffix.length());
        }
        return s;
    }

    private String getIDColumnName(String className) throws Exception {
        ClassFinder classFinder = new ClassFinder();
        List<Class> classes = classFinder.getAllClasses();
        for (Class clazz: classes){
            if(clazz.getSimpleName().equals(className)){
                for(Field field: clazz.getDeclaredFields()){
                    for(Annotation annotation: field.getAnnotations()){
                        if(annotation instanceof Id){
                            if(field.getAnnotation(Column.class) == null){
                                return field.getName();
                            }
                            else {
                                return field.getAnnotation(Column.class).name();
                            }
                        }
                    }
                }
            }
        }
        throw new Exception("No column annotated with @Id annotation in "+className+" class");
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
    public String prepareSQLAddition(List<AccessListRow> accessListRows, String sql) throws Exception {
        String result = "";
        String idColumnName = "";
        if(accessListRows.size() > 0){
            idColumnName = getIDColumnName(accessListRows.get(0).getTableName());
        }
        for(AccessListRow accessListRow: accessListRows){
            String tableName = accessListRow.getTableName().toLowerCase(Locale.ROOT);
            if(tableName.length() > 10){
                tableName = tableName.substring(0, 10);
            }
            result += " "+tableName+"0_."+idColumnName+" = "+accessListRow.getProtectedDataId()+" or ";
        }
        if(!alreadyHasWhereClause(sql)){
            result = " where "+result;
            result = DerbyQueryBuilder.removeSuffix(result, "or ");
        }
        return result;
    }

    @Override
    public String returnPreparedSQL(List<AccessListRow> accessListRows, String sql) throws Exception {
        String[] splitedSql = splitStringOnAdditionPlace(sql);
        return splitedSql[0]+prepareSQLAddition(accessListRows, sql)+splitedSql[1];
    }
}
