package org.safety.library.SQLModule;

import org.safety.library.initializationModule.utils.ClassFinder;
import org.safety.library.models.AccessListRow;
import org.safety.library.models.Role;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

public class DerbyQueryBuilder implements Builder {

    private String[] splitQueryOnSpaces(String sql) {
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
        for (Class clazz : classes) {
            if (clazz.getSimpleName().equalsIgnoreCase(className)) {
                for (Field field : clazz.getDeclaredFields()) {
                    for (Annotation annotation : field.getAnnotations()) {
                        if (annotation instanceof Id) {
                            if (field.getAnnotation(Column.class) == null) {
                                return field.getName();
                            } else {
                                return field.getAnnotation(Column.class).name();
                            }
                        }
                    }
                }
            }
        }
        throw new Exception("No column annotated with @Id annotation in " + className + " class");
    }

    @Override
    public boolean alreadyHasWhereClause(String sql) {
        for (String word : splitQueryOnSpaces(sql)) {
            if (word.toLowerCase(Locale.ROOT).equals("where")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String addACLJoin(String sql) {
        String[] splittedForACLJoin = new String[2];
        if(!alreadyHasWhereClause(sql)){
            splittedForACLJoin[0] = sql;
            splittedForACLJoin[1] = "";
        }
        else{
            splittedForACLJoin = sql.split("where");
            splittedForACLJoin[1] = " where " + splittedForACLJoin[1];
        }
        return splittedForACLJoin[0] + " , AccessList accesslist0_ " + splittedForACLJoin[1];

    }

    @Override
    public String addACLCondition(String sql, String tableName, Role role) throws Exception {
        String table = tableName.toLowerCase(Locale.ROOT);
        if (tableName.length() > 10) {
            table = tableName.substring(0, 10);
        }
        String[] splittedForACLCondition = new String[2];
        if(!alreadyHasWhereClause(sql)){
            splittedForACLCondition[0] = sql + " where ";
            splittedForACLCondition[1] = "";
        }
        else{
            splittedForACLCondition = sql.split("where");
            splittedForACLCondition[0] = splittedForACLCondition[0] + " where ( ";
            splittedForACLCondition[1] = " and  "+splittedForACLCondition[1] + " ) ";
        }
        return splittedForACLCondition[0] + " accesslist0_.protecteddataid = "+table+"0_."+getIDColumnName(tableName)+" and accesslist0_.role_id="+role.getId()+" and accesslist0_.tablename=\'"+tableName +"\'"+" and accesslist0_.canread=1"+ splittedForACLCondition[1];
    }

    @Override
    public String getModifiedSQL(String sql, String tableName, Role role) throws Exception {
        return addACLJoin(addACLCondition(sql, tableName, role));
    }
}
