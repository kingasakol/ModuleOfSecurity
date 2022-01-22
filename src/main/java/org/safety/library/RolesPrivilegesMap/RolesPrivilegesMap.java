package org.safety.library.RolesPrivilegesMap;

import org.safety.library.initializationModule.Exceptions.AddPrivillegeRowNotFoundException;
import org.safety.library.initializationModule.Exceptions.RoleForUserNotFoundException;
import org.safety.library.initializationModule.utils.DatabaseWrappers;
import org.safety.library.initializationModule.utils.PrivilegesReader;
import org.safety.library.models.AccessListRow;
import org.safety.library.models.AddPrivilege;
import org.safety.library.models.Role;

import java.util.List;

public class RolesPrivilegesMap {
    private final DatabaseWrappers databaseWrappers = new DatabaseWrappers();

    private final List <AccessListRow> privileges;
    private boolean canCreate;
    private Role concreteRole = null;

    public RolesPrivilegesMap(String tableName) {
        initConcreteRole();
        instantiateCanCrate(tableName);
        this.privileges = this.databaseWrappers.getAccessForRole(this.concreteRole);
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


    public List<AccessListRow> getPrivileges() {
        return this.privileges;
    }

    public boolean canCreate() {
        return this.canCreate;
    }
}
