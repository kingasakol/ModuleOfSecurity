package org.safety.library.models;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersRole usersRole = (UsersRole) o;
        return userId == usersRole.userId && role.equals(usersRole.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, role);
    }
}
