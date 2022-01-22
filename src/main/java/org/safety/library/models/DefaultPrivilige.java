package org.safety.library.models;

import javax.persistence.*;

@Entity
@Table(name ="default_privilige")
public class DefaultPrivilige {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)

    private long Id;

    @ManyToOne
    @JoinColumn(name="role_id", nullable = false)
    private Role role;

    private String tableName;

    private boolean canRead;
    private boolean canUpdate;
    private boolean canDelete;

    public DefaultPrivilige(Role role, String tableName, boolean canRead, boolean canUpdate, boolean canDelete) {
        this.role = role;
        this.tableName = tableName;
        this.canRead = canRead;
        this.canUpdate = canUpdate;
        this.canDelete = canDelete;
    }

    public DefaultPrivilige() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public boolean isCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public long getId() {
        return Id;
    }
}
