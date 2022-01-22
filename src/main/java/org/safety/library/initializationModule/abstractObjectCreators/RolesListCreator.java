package org.safety.library.initializationModule.abstractObjectCreators;

import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.abstractMappingObjects.RolesList;
import org.safety.library.models.DefaultPrivilige;
import org.safety.library.models.Role;

import java.util.*;


public class RolesListCreator {

    public RolesList createRolesList(JSONMapping mapping){
        List<List<String>> rolesList = mapping.getMappedData();
        rolesList.remove(0);
        List<Role> result = new LinkedList<Role>();
        for(List<String> roleWithPrivs : rolesList){
            Role newRole = new Role(roleWithPrivs.get(0));
            List<DefaultPrivilige> privList = new LinkedList<DefaultPrivilige>();
            int i = 1;
            while( i+3 < roleWithPrivs.size()){
                String name = roleWithPrivs.get(i);
                boolean canRead = this.parseString(roleWithPrivs.get(i+1));
                boolean canUpdate = this.parseString(roleWithPrivs.get(i+2));
                boolean canDelete = this.parseString(roleWithPrivs.get(i+3));
                DefaultPrivilige priv = new DefaultPrivilige(newRole, name, canRead, canUpdate, canDelete);
                privList.add(priv);
                i += 4;
            }
            newRole.setDefaultPriviliges(privList);
            result.add(newRole);
            System.out.println(newRole.getDefaultPriviliges());
        }
        return new RolesList(result);
    }

    private boolean parseString(String param){
        if(param.equals("true")){
            return true;
        }
        else{
            return false;
        }
    }

}
