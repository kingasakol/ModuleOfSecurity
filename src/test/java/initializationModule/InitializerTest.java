package initializationModule;

import org.junit.jupiter.api.Test;
import org.safety.library.initializationModule.Initializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InitializerTest {

    @Test
    public void readsCorrectJSONPathFromAnnotation() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Initializer initializer = new Initializer();
        Method method = initializer.getClass().getDeclaredMethod("getRolesPathFromAnnotation");
        method.setAccessible(true);

        String path = (String) method.invoke(initializer);

        assertEquals(path, "exampleTestFiles/exampleJSONs/validJSONs/dataExampleRolesForUsersJSONFile.json");
    }
}
