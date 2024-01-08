# 2장. 문자열 계산기 구현을 통한 테스트와 리팩토링
## main() 메서드를 통한 테스트의 문제점

- 클래스가 가지고 있는 모든 메서드를 테스트할 수 밖에 없다.
- 테스트 결과를 매번 콘솔에 출력되는 값을 통해 수동으로 확인해야 한다.
- 프로덕션 코드의 복잡한 로직을 머릿 속으로 계산해 결과 값이 정상적으로 출력되는지 일일히 확인해야 한다.

⇒ JUnit 라이브러리 등장

## JUnit을 활용해 main 메서드 문제점 극복

```java
import org.junit.Test;

public class CalculatorTest {
	@Test
	public void add() {
		Calculator cal = new Calculator();
		System.out.println(cal.add(6,3));
	}

	@Test
	public void substract() {
		Calculator cal = new Calculator();
		System.out.println(cal.substract(6,3));
	}
}
```

- 각각의 테스트 메서드를 독립적으로 실행 가능하다.
- 내가 구현하고 있는 프로덕션 코드의 메서드만 실행해볼 수 있다.

## 결과 값을 눈이 아닌 프로그램을 통해 자동화

- main() 메서드의 문제점은 실행 결과를 눈으로 직접 확인해봐야 한다는 것임.
- JUnit은 이 같은 문제점을 극복하기 위해 assertEquals() 메서드를 제공한다.

```java
**import org.junit.Assert.assertEquals;**

import org.junit.Test;

public class CalculatorTest {
	@Test
	public void add() {
		Calculator cal = new Calculator();
		**assertEquals(9, cal.add(6,3));**
	}

	@Test
	public void substract() {
		Calculator cal = new Calculator();
		**assertEquals(3, cal.substract(6,3));**
	}
}
```

- JUnit의 Assert 클래스는 assertEquals() 이외에도 assertTrue(), assertFalse() / assertNull(), assertNotNull() / assertArrayEquals() 메서드를 제공한다.