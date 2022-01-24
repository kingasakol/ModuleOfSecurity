package org.safety.library.RolesPrivilegesMap;

import org.safety.library.initializationModule.Exceptions.RoleForUserNotFoundException;
import org.safety.library.initializationModule.utils.DatabaseWrappers;
import org.safety.library.initializationModule.utils.PrivilegesReader;
import org.safety.library.models.AccessListRow;
import org.safety.library.models.AddPrivilege;
import org.safety.library.models.Role;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RolesPrivilegesMap {
    private final DatabaseWrappers databaseWrappers;

    private final List<AccessListRow> privileges;
    private final List<AccessListRow> filteredList;
    private Role concreteRole = null;
    private boolean canCreate;


    public RolesPrivilegesMap(DatabaseWrappers databaseWrappers, String tableName) {
        this.databaseWrappers = databaseWrappers;
        System.out.println("Hop w przepaść");
        initConcreteRole();
        System.out.println("initConcreteRole");
        instantiateCanCrate(tableName);
        System.out.println("instantiateCanCrate");
        this.privileges = this.databaseWrappers.getAccessForRole(this.concreteRole);
        System.out.println("privileges");
        this.filteredList = filterList(tableName);
        System.out.println("filteredList");
    }

    private void initConcreteRole() {
        try {
            this.concreteRole = PrivilegesReader.getRole(databaseWrappers);
        } catch (RoleForUserNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void instantiateCanCrate(String tableName) {
        List<AddPrivilege> addPrivilegeList = databaseWrappers.getAddPrivilege(this.concreteRole);
        for (AddPrivilege addPrivilege : addPrivilegeList) {
            if(addPrivilege.getTableName().equals(tableName)) {
                this.canCreate = true;
                return;
            }
        }
        this.canCreate = false;
    }

    private List<AccessListRow> filterList(String entityName) {
        Stream<AccessListRow> accessListRowStream = this.privileges.stream();
        accessListRowStream
                .filter(accessListRow -> (accessListRow.isCanRead()))
                .filter(accessListRow -> accessListRow.getTableName().equals(entityName))
                .filter(accessListRow -> accessListRow.getRole() == this.concreteRole);
        return accessListRowStream.collect(Collectors.toList());
    }

    public List<AccessListRow> getFilteredList() {
        return filteredList;
    }

    public List<AccessListRow> getPrivileges() {
        return this.privileges;
    }

    public boolean canCreate() {
        return this.canCreate;
    }
}
