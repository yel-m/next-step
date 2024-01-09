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

## 테스트 코드 중복 제거

### Before 메서드

- JUnit은 테스트를 진행하기 위한 초기화 작업을 `@Before` 애노테이션을 활용해 다음과 같이 구현할 것을 추천한다.

```java
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
	private Calculator cal;

	**@Before
	public void setup() {
		cal = new Calculator();
	}**

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

- 이와 같이 Calculator 인스턴스를 매 테스트마다 생성하는 이유는 add() 메서드를 실행할 때 Calculator 상태 값이 변경되어 다음 테스트 메서드인 substract() 테스트 메서드를 실행할 때 영향을 미칠 수 있기 때문이다.
- 테스트 메서드 간 영향을 미칠 경우 테스트 실행 순서나 Calculator 상태 값에 따라 테스트가 성공하거나 실패할 수 있다.
- Before 애너테이션을 추천하는 이유
    - JUnit에는 @RunWith, @Rule 같은 애너테이션을 사용해 기능을 확장할 수 있는데, @Before 안이어야만 @RunWith, @Rule에서 초기화된 객체에 접근할 수 있다는 제약 사항이 있기 때문이다.

### After 메서드

- 메서드 실행이 끝난 후 실행됨으로써 후처리 작업을 담당한다.