import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringCalculatorTest {

    StringCalculator cal;

    @Before
    public void setUp() {
        cal = new StringCalculator();
    }

    @Test
    public void setEmptyString() {
        assertEquals(0, cal.add(" "));
    }

    @Test
    public void setStringWithDefaultSeparator() {
        assertEquals(6, cal.add("1,2:3"));
    }

    // 콜론, 쉼표 테스트케이스 더 작성해야 하나?
    // 테스트 케이스 메서드 너무 긴가?

    @Test
    public void setStringWithCustomSeparatorString() {
        assertEquals(6, cal.add("//$\n1$2$3"));
    }
}