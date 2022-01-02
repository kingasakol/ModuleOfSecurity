package org.safety.library.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProtectedData {
    public String jsonPath() default "";

    //the user should define the name of the ID column if it differs from standard name
    public String idColumn() default "id";
}
