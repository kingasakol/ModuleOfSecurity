package helpers;

import org.mockito.Mockito;
import org.safety.library.initializationModule.JSONMapping;

import java.util.*;

public class DataAccessCreatorTestStubs {
    public static List<List<String>> stubbedFactoryResults = new LinkedList<>(Arrays.asList(
            new LinkedList<>(Arrays.asList("SomeClass")),
            new LinkedList<>(Arrays.asList("1", "admin","ksiegowy", "HR")),
            new LinkedList<>(Arrays.asList("2", "admin", "hacker", "prezesi")),
            new LinkedList<>(Arrays.asList("3", "admin", "tester", "HR")),
            new LinkedList<>(Arrays.asList("4", "admin", "signedOutUser", "tester")),
            new LinkedList<>(Arrays.asList("5", "admin", "hacker", "ksiegowy", "prezesi"))
    ));



}
