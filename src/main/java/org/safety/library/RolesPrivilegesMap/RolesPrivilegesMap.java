package org.safety.library.RolesPrivilegesMap;

import org.safety.library.models.AccessListRow;

import java.util.List;

public class RolesPrivilegesMap {
    private List<AccessListRow> privileges;
    private boolean create;

    //creates privileges field
    private void instantiatePrivileges(){
        //TODO
    }

    //creates create field
    private void instantiateCanCrate(){
        //TODO
    }


    public List<AccessListRow> getPrivileges(){
        return this.privileges;
    }

    public boolean canCreate(){
        return this.create;
    }

    public void setPrivileges(List<AccessListRow> privileges) {
        this.privileges = privileges;
    }
}
