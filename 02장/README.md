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

## 테스트와 리팩토링

- 복잡한 문제를 풀어가기 위해 첫 번째로 진행해야 하는 작업이 복잡한 문제를 작은 단위로 나눠 좀 더 쉬운 문제로 만드는 작업이다.
- 3가지 원칙
  - 메서드가 한 가지 책임만 가지도록 구현한다.
  - 인덴트 깊이를 1단계로 유지한다.
  - else를 사용하지 마라.

### 모든 단계의 끝은 리팩토링

- 소스코드의 복잡도가 쉽게 증가하는 이유는 하나의 요구사항을 완료한 후 리팩토링을 하지 않은 상태에서 다음 단계로 넘어가기 때문이다.
- 각 단계에서 다음 단계로 넘어가기 위한 작업의 끝은 기대하는 결과를 확인했을 때가 아니라 결과를 확인한 후 리팩토링까지 완료했을 때이다.
- 리팩토링을 통해 프로덕션 코드를 변경하더라도 테스트 코드가 있으면 바로 검증할 수 있기 때문에 부담없이 연습할 수 있다.
- **리팩토링을 통해 소스코드를 개선하는 작업을 하고 싶다면 테스트 코드가 뒷받침되어야 한다.**

## 추가 학습 자료

### 테스트 주도 개발(TDD)과 리팩토링

> TDD를 바로 연습하는 것도 좋지만 그보다는 JUnit을 활용해 테스트를 검증하는 방식으로 진행하다 한 단계 더 성장하고 싶을 때 도전할 것을 추천한다.
>

이 두 주제를 학습하는 단계는 다음과 같이 할 수 있다.

- 먼저 “테스트 주도 개발 : 고품질 쾌속 개발을 위한 TDD 실천법과 도구” 책의 1장 공개 자료인 [goo.gle/2ny56W를](http://goo.gle/2ny56W를) 통해 TDD가 무엇인지, 등장 배경은 무엇이며 어떤 효과가 있는지 검토해본다. 이 문서를 읽고 실습해본 후 이 장의 문자열 계산기를 TDD 방식으로 구현해본다.
- 그 다음 “[테스트 주도 개발](https://www.yes24.com/Product/Goods/12246033)” 책과 “[리팩토링 : 코드 품질을 개선하는 객체지향 사고법](https://product.kyobobook.co.kr/detail/S000001223881)”을 볼 것을 추천한다. TDD와 리팩토링에 대한 기본 원리, 실천 방법에 대해 자세하게 다루는 책이다.
- “리팩토링” 책의 경우 5장부터는 전체 리팩토링에 대한 카탈로그를 제시하고 있기 때문에 책을 끝까지 읽기보다 4장까지는 반드시 읽고 나머지 카탈로그는 자신이 구현한 코드를 리팩토링하면서 카탈로그를 참조하는 방식으로 접근해도 좋다.
- TDD와 리팩토링을 연습할 때 너무 복잡한 로직을 포함하고 있거나 외부 의존 관계가 많은 코드보다는 알고리즘이나 유틸리티 성격의 코드를 구현하면서 연습하는 게 좋다.
- TDD로 충분히 연습을 한 후 다음 단계로 점차 복잡도가 높거나 외부와의 의존관계가 있는 코드로 확대해 나가는 방식으로 접근하는 것이 TDD를 체득하고 나만의 습관으로 만들 수 있는 좋은 길이다.

### 정규 표현식

- “[손에 잡히는 정규 표현식](https://m.yes24.com/Goods/Detail/3475120)” 책 추천
- 정규 표현식은 언젠가는 개발자가 넘어야 할 산이다. 하지만 더 중요한 부분에 대한 학습을 완료한 후 문자열 조작이 많아지는 시점에 학습할 것을 추천한다.
- [http://regexr.com/](https://regexr.com/) 와 같이 온라인 상에서 연습할 수 있는 곳도 있다.