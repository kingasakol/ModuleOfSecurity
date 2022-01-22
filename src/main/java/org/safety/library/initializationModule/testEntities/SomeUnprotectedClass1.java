package org.safety.library.initializationModule.testEntities;

import javax.persistence.*;

@Entity

public class SomeUnprotectedClass1 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    private String someValue;
    private String someOtherValue;

    public String getSomeValue() {
        return someValue;
    }

    public void setSomeValue(String someValue) {
        this.someValue = someValue;
    }

    public String getSomeOtherValue() {
        return someOtherValue;
    }

    public void setSomeOtherValue(String someOtherValue) {
        this.someOtherValue = someOtherValue;
    }

    public SomeUnprotectedClass1(String someValue, String someOtherValue) {
        this.someValue = someValue;
        this.someOtherValue = someOtherValue;
    }

    public SomeUnprotectedClass1() {}
}
