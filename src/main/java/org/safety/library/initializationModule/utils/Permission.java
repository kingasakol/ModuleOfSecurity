package org.safety.library.initializationModule.utils;

import org.safety.library.models.Role;

import java.util.Objects;

public class Permission {
    private Role role;

    private int dataId;

    private boolean canRead;

    private boolean canUpdate;

    private boolean canDelete;

    public Permission(Role role, int dataId, boolean canRead, boolean canUpdate, boolean canDelete) {
        this.role = role;
        this.dataId = dataId;
        this.canRead = canRead;
        this.canUpdate = canUpdate;
        this.canDelete = canDelete;
    }

    public Role getRole() {
        return role;
    }

    public int getDataId() {
        return dataId;
    }

    public boolean isCanRead() {
        return canRead;
    }

    public boolean isCanUpdate() {
        return canUpdate;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return dataId == that.dataId && canRead == that.canRead && canUpdate == that.canUpdate && canDelete == that.canDelete && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, dataId, canRead, canUpdate, canDelete);
    }
}
