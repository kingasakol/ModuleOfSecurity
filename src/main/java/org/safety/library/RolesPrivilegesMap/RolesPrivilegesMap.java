package org.safety.library.RolesPrivilegesMap;

import org.safety.library.initializationModule.Exceptions.AddPrivillegeRowNotFoundException;
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
    private final DatabaseWrappers databaseWrappers = new DatabaseWrappers();

    private final List <AccessListRow> privileges;
    private List<AccessListRow> filteredList;
    private boolean canCreate;
    private Role concreteRole = null;

    public RolesPrivilegesMap(String tableName) {
        initConcreteRole();
        instantiateCanCrate(tableName);
        this.privileges = this.databaseWrappers.getAccessForRole(this.concreteRole);
        filterList(tableName);
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

    private void filterList(String entityName) {
        Stream<AccessListRow> accessListRowStream = this.privileges.stream();
        accessListRowStream
                .filter(accessListRow -> (accessListRow.isCanRead()))
                .filter(accessListRow -> accessListRow.getTableName().equals(entityName))
                .filter(accessListRow -> accessListRow.getRole() == this.concreteRole);
        this.filteredList = accessListRowStream.collect(Collectors.toList());
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
