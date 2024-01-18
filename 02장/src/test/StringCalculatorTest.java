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
    void setStringWithDefaultSeparator1() {
        Assertions.assertEquals(6, cal.add("1,2,3"));
    }

    @Test
    void setStringWithDefaultSeparator2() {
        Assertions.assertEquals(6, cal.add("1:2:3"));
    }

    @Test
    void setStringWithDefaultSeparator3() {
        Assertions.assertEquals(6, cal.add("1,2:3"));
    }

    @Test
    void setNegativeStringWithDefaultSeparator() {
        Assertions.assertEquals(6, cal.add("1,-2:-3"));
    }

    // 콜론, 쉼표 테스트케이스 더 작성해야 하나?
    // 테스트 케이스 메서드 너무 긴가?

    @Test
    void setStringWithCustomSeparatorString() {
        Assertions.assertEquals(6, cal.add("//$\n1$2$3"));
    }

    @Test
    void failOnNegativeInput() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            cal.add("//^\n-1^-2^-3");
        });
    }
}