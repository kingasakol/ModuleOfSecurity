package org.safety.library.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<DefaultPrivilige> privs;

    public Role() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDefaultPriviliges(List<DefaultPrivilige> privs){
        this.privs = privs;
    }

    public List<DefaultPrivilige> getDefaultPriviliges() {
        return privs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "\nRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", privs=" + privs +
                '}';
    }
}
