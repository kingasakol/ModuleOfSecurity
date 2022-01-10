package helpers;

import org.mockito.Mockito;
import org.safety.library.initializationModule.JSONMapping;

import java.util.*;

public class DataAccessCreatorTestStubs {
    public static List<List<String>> stubbedFactoryResults = new LinkedList<>(Arrays.asList(
            new LinkedList<>(Arrays.asList("SomeClass")),
            new LinkedList<>(Arrays.asList("1", "admin","true","true","true","ksiegowy","true","false","false","HR","true","false","false")),
            new LinkedList<>(Arrays.asList("2", "admin","true","true","false", "hacker","true","true","false", "prezesi", "true", "true", "true")),
            new LinkedList<>(Arrays.asList("3", "admin","true","true","true", "tester","true","true","false", "HR","true","true","false")),
            new LinkedList<>(Arrays.asList("4", "admin","true","true","true", "signedOutUser","true","false","false", "tester","true","true","false")),
            new LinkedList<>(Arrays.asList("5", "admin", "true","true","true", "hacker","false","false","false", "ksiegowy","true","false","false", "prezesi", "true", "true", "true"))
    ));



}
