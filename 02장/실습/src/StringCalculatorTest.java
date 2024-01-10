import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorTest {
    StringCalculator cal;

    @BeforeEach
    void setUp() {
        cal = new StringCalculator();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setEmptyString() {
        assertEquals(0, cal.add(" "));
    }

    @Test
    void setStringWithDefaultSeparator() {
        assertEquals(6, cal.add("1,2:3"));
    }

    // 콜론, 쉼표 테스트케이스 더 작성해야 하나?
    // 테스트 케이스 메서드 너무 긴가?

    @Test
    void setStringWithCustomSeparatorString() {
        assertEquals(6, cal.add("//$\n1$2$3"));
    }


}