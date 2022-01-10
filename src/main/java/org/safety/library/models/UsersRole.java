package org.safety.library.models;

import javax.persistence.*;

@Entity
@Table(name="users_role")
public class UsersRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @OneToOne //???
    @JoinColumn(name="role_id", nullable = false)
    private Role role;

    private String tableName;

    public UsersRole(int userId, Role role) {
        this.userId = userId;
        this.role = role;
    }

    public UsersRole() {

    }

    public int getUserId() {
        return userId;
    }

    public Role getRole() {
        return role;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
