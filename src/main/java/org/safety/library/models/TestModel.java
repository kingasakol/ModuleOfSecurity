package org.safety.library.models;

import javax.persistence.*;

@Entity
@Table(name = "test_model")
public class TestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long testModelID;

    private String someValue;

    public TestModel(String someValue) {
        this.someValue = someValue;
    }

    public String getSomeValue() {
        return someValue;
    }

    public void setSomeValue(String someValue) {
        this.someValue = someValue;
    }

    public TestModel() {}


}
