package initializationModule.utils;

import helpers.ClassFinderHelper;
import org.junit.jupiter.api.Test;
import org.safety.library.initializationModule.utils.ClassFinder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ClassFinderTest {



    @Test
    public void findsAllClassesInTheProject() throws IOException, ClassNotFoundException, URISyntaxException {
        ClassFinder classFinder = new ClassFinder();

        List<Class> classes = classFinder.getAllClasses();
        Set<Object> currentClassesSet = Set.of(classes.stream().map(Class::getName).toArray());

        assert(currentClassesSet.containsAll(ClassFinderHelper.currentClassNames));
    }
}
