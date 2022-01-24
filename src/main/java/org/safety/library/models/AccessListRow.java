package org.safety.library.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AccessList")
public class AccessListRow {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    private int protectedDataId;

    private String tableName;

    private boolean canRead;

    private boolean canDelete;

    private boolean canUpdate;


    public AccessListRow(Role role, int protectedDataId, String tableName, boolean canRead, boolean canDelete, boolean canUpdate) {
        this.role = role;
        this.protectedDataId = protectedDataId;
        this.tableName = tableName;
        this.canRead = canRead;
        this.canDelete = canDelete;
        this.canUpdate = canUpdate;
    }

    public AccessListRow() {}

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getProtectedDataId() {
        return protectedDataId;
    }

    public void setProtectedDataId(int protectedDataId) {
        this.protectedDataId = protectedDataId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isCanRead() {
        return canRead;
    }

    public void setCanRead(boolean canRead) {
        this.canRead = canRead;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    @Override
    public String toString() {
        return "\nAccessListRow{" +
                "id=" + id +
                ", role=" + role +
                ", protectedDataId=" + protectedDataId +
                ", tableName='" + tableName + '\'' +
                ", canRead=" + canRead +
                ", canDelete=" + canDelete +
                ", canUpdate=" + canUpdate +
                '}';
    }

    public boolean isCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessListRow that = (AccessListRow) o;
        return protectedDataId == that.protectedDataId && canRead == that.canRead && canDelete == that.canDelete && canUpdate == that.canUpdate && role.equals(that.role) && tableName.equals(that.tableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, protectedDataId, tableName, canRead, canDelete, canUpdate);
    }
}
