package helpers;

import org.mockito.Mockito;
import org.safety.library.initializationModule.JSONMapping;

import java.util.List;

public class JSONMappingStubs {
    public static JSONMapping getJSONMappingMock(List<List<String>> results){
        JSONMapping mapping = Mockito.mock(JSONMapping.class);
        Mockito.when(mapping.getMappedData()).thenReturn(results);
        return mapping;
    }
}
