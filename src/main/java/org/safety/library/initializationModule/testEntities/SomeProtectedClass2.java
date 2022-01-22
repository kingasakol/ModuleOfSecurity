package org.safety.library.initializationModule.testEntities;

import org.safety.library.annotations.ProtectedData;

import javax.persistence.*;

@Entity
@ProtectedData(jsonPath = "exampleTestFiles/exampleJSONs/integrationTestJSONs/DataAccess2.json")
public class SomeProtectedClass2 {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public SomeProtectedClass2(String someValue, String someOtherValue, Long id) {
        this.someValue = someValue;
        this.someOtherValue = someOtherValue;
        this.id = id;
    }

    public SomeProtectedClass2() {}
}
