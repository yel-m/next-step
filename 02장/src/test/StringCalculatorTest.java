import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class StringCalculatorTest {

    StringCalculator cal;

    @BeforeEach
    void setUp() {
        cal = new StringCalculator();
    }

    @Test
    void add_null_또는_빈문자() {

        Assertions.assertEquals(0, cal.add(" "));
        Assertions.assertEquals(0, cal.add(null));
    }

    @Test
    void add_숫자하나() throws Exception {
        Assertions.assertEquals(1, cal.add("1"));
    }

    @Test
    void add_쉼표_구분자() throws Exception{
        Assertions.assertEquals(6, cal.add("1,2,3"));
    }

    @Test
    void add_콜론_구분자() throws Exception{
        Assertions.assertEquals(6, cal.add("1:2:3"));
    }

    @Test
    void add_쉼표_또는_콜론_구분자() throws Exception{
        Assertions.assertEquals(6, cal.add("1,2:3"));
    }

    @Test
    void add_custom_구분자() throws Exception{
        Assertions.assertEquals(6, cal.add("//$\n1$2$3"));
    }

    @Test
    void add_negative() throws Exception {
        Assertions.assertThrows(RuntimeException.class, () -> {
            cal.add("1,-2:-3");
        });
    }
}