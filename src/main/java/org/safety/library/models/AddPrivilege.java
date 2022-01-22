package org.safety.library.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "add_privilege")
public class AddPrivilege {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    private String tableName;

    public AddPrivilege(Role role, String tableName) {
        this.role = role;
        this.tableName = tableName;
    }


    public AddPrivilege() {

    }

    public int getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddPrivilege that = (AddPrivilege) o;
        return role.equals(that.role) && tableName.equals(that.tableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, tableName);
    }
}
