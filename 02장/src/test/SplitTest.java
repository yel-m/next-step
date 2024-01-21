import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SplitTest {
    @Test
    void split() {
        Assertions.assertArrayEquals(new String[] {"1"}, "1".split(","));
        Assertions.assertArrayEquals(new String[] {"1", "2"}, "1,2".split(","));
    }
}
