package org.safety.library.models;

import javax.persistence.*;

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

    public HibernateSelect() {}
}
