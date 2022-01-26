package org.safety.library.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class HibernateSelect {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    private String hibernateSelectName;
    private String entityName;

    public String getHibernateSelectName() {
        return hibernateSelectName;
    }

    public void setHibernateSelectName(String hibernateSelectName) {
        this.hibernateSelectName = hibernateSelectName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public HibernateSelect(String hibernateSelectName, String entityName) {
        this.hibernateSelectName = hibernateSelectName;
        this.entityName = entityName;
    }

    public HibernateSelect() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HibernateSelect that = (HibernateSelect) o;
        return hibernateSelectName.equals(that.hibernateSelectName) && entityName.equals(that.entityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hibernateSelectName, entityName);
    }
}
