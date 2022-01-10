package helpers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EntityAccessCreatorStubs {
    public static List<List<String>> stubbedFactoryResults = new LinkedList<>(Arrays.asList(
            new LinkedList<>(Arrays.asList("Klasa1", "admin","ksiegowy", "HR")),
            new LinkedList<>(Arrays.asList("Klasa2", "admin", "hacker", "prezesi")),
            new LinkedList<>(Arrays.asList("Klasa3", "admin", "tester", "HR")),
            new LinkedList<>(Arrays.asList("InnaKlasa", "admin", "signedOutUser", "tester")),
            new LinkedList<>(Arrays.asList("KolejnaKlasa", "admin", "hacker", "ksiegowy", "prezesi"))
    ));
}
