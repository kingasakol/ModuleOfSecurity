package org.safety.library.RolesPrivilegesMap;

import org.safety.library.initializationModule.Exceptions.AccessListRowNotFoundException;
import org.safety.library.initializationModule.Exceptions.AddPrivillegeRowNotFoundException;
import org.safety.library.initializationModule.Exceptions.RoleForUserNotFoundException;
import org.safety.library.initializationModule.utils.DatabaseWrappers;
import org.safety.library.initializationModule.utils.PriviledgesReader;
import org.safety.library.models.AccessListRow;
import org.safety.library.models.AddPrivilege;
import org.safety.library.models.Role;

import java.util.List;

public class RolesPrivilegesMap {
    private DatabaseWrappers databaseWrappers;
    private PriviledgesReader priviledgesReader;
    private String tableName = null;

    private List<AccessListRow> privileges;
    private boolean canCreate;

    private Role concreteRole = null;


    public RolesPrivilegesMap(DatabaseWrappers databaseWrappers, PriviledgesReader priviledgesReader) {
        this.databaseWrappers = databaseWrappers;
        this.priviledgesReader = priviledgesReader;
        initConcreteRole();
        instantiatePrivileges();
    }

    //for create method we need tableName
    public RolesPrivilegesMap(DatabaseWrappers databaseWrappers, PriviledgesReader priviledgesReader, String tableName) {
        this.databaseWrappers = databaseWrappers;
        this.priviledgesReader = priviledgesReader;
        this.tableName = tableName;
        initConcreteRole();
        instantiatePrivileges();
        instantiateCanCrate(tableName);
    }

    private void initConcreteRole() {
        try {
            this.concreteRole = this.priviledgesReader.getRole();
        } catch (RoleForUserNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void instantiatePrivileges() {
        this.privileges = this.databaseWrappers.getAccessForRole(this.concreteRole);
    }


    private void instantiateCanCrate(String tableName) {
        try {
            List<AddPrivilege> addPrivilegeList = databaseWrappers.getAddPrivillege(this.concreteRole);
            for(AddPrivilege addPrivilege: addPrivilegeList) {
                if(addPrivilege.getTableName().equals(tableName)) {
                    this.canCreate = true;
                } else {
                    this.canCreate = false;
                }
            }
        } catch (AddPrivillegeRowNotFoundException e) {
            e.printStackTrace();
        }
    }




    public List<AccessListRow> getPrivileges(){
        return this.privileges;
    }

    public boolean canCreate(){
        return this.canCreate;
    }

    public void setPrivileges(List<AccessListRow> privileges) {
        this.privileges = privileges;
    }
}
