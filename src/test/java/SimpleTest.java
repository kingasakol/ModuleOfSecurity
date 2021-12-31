import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SimpleTest {
    @Test
    public void testAdd() {
        assertEquals(42, Integer.sum(19, 23));
        assertNotEquals(2, Integer.sum(1, 2));
    }
}
