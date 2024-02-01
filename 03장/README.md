# 웹 서버 실습 전 학습 내용
```
필요하다고 생각한 것만 정리했다.
```
## 리눅스
- [리눅스 커맨드 라인 완벽 입문서](https://m.yes24.com/Goods/Detail/8208026)
  - 영어 버전 무료 다운 : https://linuxcommand.org/tlcl.php
## 크롬 개발자 도구
- [[특강] 크롬을 활용한 프론트엔드 디버깅](https://www.youtube.com/playlist?list=PLz4XWo74AOadtliOuRh0c4Szc26Zy6xzD)

# 소스코드 분석
- HTTP 웹서버의 핵심이 되는 코드는 [RequestHandler](https://github.com/yel-m/next-step/blob/9a470765691b15885a19e57e9b6f739e96349379/03%EC%9E%A5/src/main/java/webserver/RequestHandler.java) 클래스이다.
- [WebServer](https://github.com/yel-m/next-step/blob/9a470765691b15885a19e57e9b6f739e96349379/03%EC%9E%A5/src/main/java/webserver/WebServer.java) 클래스는 **웹 서버를 시작**하고, 사용자의 요청이 있을 때까지 대기 상태에 있다가 사용자 요청이 있을 경우 사용자의 요청을 RequestHandler 클래스에 위임하는 역할을 한다.
```java
public class WebServer {
    private static final Logger log = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                RequestHandler requestHandler = new RequestHandler(connection);
                requestHandler.start();
            }
        }
    }
}

```
- 사용자 요청이 발생할 때까지 대기 상태에 있도록 지원하는 역할은 자바에 포함되어 있는 ServerSocket 클래스가 담당한다.
- WebServer 클래스는 ServerSocket에 사용자 요청이 발생하는 순간 클라이언트와 연결을 담당하는 Sokcet을 RequestHandler에 전달하면서 **새로운 스레드를 실행**하는 방식으로 **멀티스레드 프로그래밍**을 지원하고 있다.
- RequestHandler 클래스는 Thread를 상속하고 있으며, 사용자 요청에 대한 처리와 응답에 대한 처리를 담당하는 가장 중심이 되는 클래스다.
```java

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

```
- 앞으로 진행할 모든 실습은 RequestHandler 클래스의 run() 메소드에서 구현 가능
- run() 메소드에서 InputStream은 클라이언트(웹 브라우저)로 요청을 보낼 때 전달되는 데이터, OuputStream은 서버에서 클라이언트에 응답을 보낼 때 전달되는 데이터를 담당하는 스트림이다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
#### [자바의 입출력](https://wikidocs.net/226)
다음 코드는 `BufferedReader`를 이용해 문자열을 입력을 받는 예제 코드이다.
```java
InputStream in = System.in;
InputStreamReader reader = new InputStreamReader(in);
BufferedReader br = new BufferedReader(reader);

String a = br.readLine();
```
* `InputStream`: byte를 읽는다. 
* `InputStreamReader`: character(문자)를 읽는다. 
* `BufferedReader`: String(문자열)을 읽는다.

`InputStreamReader`는  객체를 생성할 때 생성자의 입력으로 `InputStream` 객체가 필요하다. 
`RequestHandler`에서는 `connection.getInputStream()`을 통해 받아온 `InputStream` 객체를 `InputStreamReader`의 입력으로 넣어주고,
해당 `InputStreamReader`의 객체를 `BufferedReader` 객체를 생성할 때 입력으로 넣어주어 클라이언트로부터의 요청을 문자열 형태로 입력 받았다. 

#### 디버그를 위한 로깅
_**System.out.println()의 문제점**_
- System.out.println()으로 디버깅 메시지를 출력하면 파일로 메시지가 출력하게 된다.
- 파일에 메시지를 출력하는 작업은 상당한 비용이 발생한다.
- 이 같은 단점을 보완하기 위해 등장한 라이브러리가 로깅 라이브러리이다.
- 자바 진영에서 많이 사용하는 로깅 라이브러리는 Logback이다.

_**SLF4J 사용하기**_
- 자바 진영은 많은 로깅 라이브러리 구현체가 존재한다.
- 그런데 더 좋은 구현체가 등장할 때마다 전체 소스코드에서 로깅 라이브러리 구현 부분을 수정하는 어려움이 있다.
- 이 같은 어려움을 해소하기 위해 SLF4J라는 라이브러리를 활용해 API에 대한 창구를 일원화 했다.
- 더 좋은 로깅 라이브러리가 등장할 경우 소스코드는 수정할 필요 없이 구현체를 담당할 로깅 라이브러리만 교체하면 된다.
- RequestHandler에서 로깅 라이브러리를 사용한 부분을 살펴보면 다음과 같다.
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {
  private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
  
  [...]

  public void run() {
    log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());
```
- 해당 프로젝트는 로깅 구현체로 Logback 라이브러리를 사용하고 있다.
- 하지만 Logback 라이브러리를 직접 사용하지 않고 SLF4J를 사용하고 있다.
- Logback 라이브러리에 대한 구현체는 메이븐 설정 파일인 pom.xml에 정의하고 있다.

_**로그레벨**_
- 자바 진영의 로깅 라이브러리는 메시지 출력 여부를 로그 레벨을 통해 관리한다. 
- 대표적인 로그 레벨은 TRACE, DEBUG, INFO, WARN, ERROR가 있다.
- 로그 레벨은 TRACE < DEBUG < INFO < WARN < ERROR 순으로 높아진다.
- 로그 레벨이 높을수록 출력되는 메시지는 적어지고, 로그 레벨이 낮을수록 더 많은 로깅 레벨이 출력된다.
- 로그 메시지를 출력할 때 눈여겨 볼 부분 중의 하나는 메시지를 생성하는 부분이다.
- 로그 메시지를 출력할 경우 다음과 같이 메시지를 구현하는 것이 일반적이다.
  ```java
  log.debug("New Client Connect! Connected IP : " + connection.getInetAddress() + ", Port : " + connection.getPort());
  ```
- 그런데 이와 같이 구현할 경우 로그 레벨이 INFO, WARN인 경우 굳이 debug() 메서드에 인자를 전달하기 위해 문자열을 더하는 부분이 실행될 필요가 없다.
- 자바에서 문자열을 더하는 비용은 예상보다 큰데, 로깅 레벨이 높아 굳이 실행할 필요가 없음에도 불구하고 실행됨으로써 애플리케이션의 성능을 떨어뜨린다.
- 성능을 떨어뜨리지 않으면서 동적인 메시지를 구현하려면 다음과 같이 구현할 수 있다.
  ```java
  log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());
  ```
_**logback.html**_
- Logback은 로그레벨과 메시지 형식에 대한 설정 파일은 logback.xml이다. 
- 로깅 라이브러리를 활용하면 출력한 로그 메시지의 패턴도 변경할 수 있다.
- 보통 개발 단계에서는 DEBUG와 같이 낮은 로그 레벨로 설정하다 실서비스로 배포할 때 INFO, WARN과 같은 로그 레벨로 설정함으로써 개발 단계에서 디버깅을 출력하는 로그를 출력하지 않도록 설정한다.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration>
<configuration>
  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <layout class="ch.qos.logback.classic.PatternLayout">
      <Pattern>%d{HH:mm:ss.SSS} [%-5level] [%thread] [%logger{36}] - %m%n</Pattern>
    </layout>
  </appender>

  <root level="DEBUG">
    <appender-ref ref="STDOUT" />
  </root>
</configuration>
```
#### 파일 데이터 읽기
_**Path**_

Path를 통해, 파일, 디렉토리 옮기기, 복사, 삭제, 읽기, 쓰기 등의 작업을 수행할 수 있다.
뿐만 아니라, 파일 시스템과 소통할 수 있는 방법을 제공하므로, 디렉토리 생성, 파일 존재 여부 확인, 정보 불러오기 등의 작업을 수행할 수 있다.

Paths 클래스는 Path를 다룰 수 있는 여러 메서드를 제공한다.
Paths 또한 java.nio.file 패키지에 속해있다.

중요한 점은, Path는 단순히 경로정보를 캡슐화한 것이다. 파일의 존재 여부는 상관 없다.

_**File**_

File은 파일 시스템에서의 파일과 디렉토리에 대한 representation을 제공한다.
Files는 파일 시스템을 통해, 파일을 다루는 여러 유틸리티 메서드를 제공한다.

Files는 File과 달리, 주로 Path객체를 사용해 메서드를 사용한다.

```java
 byte[] body = Files.readAllBytes(new File("./webapp" + requestUrl).toPath());
```
### 요구사항 2 - get 방식으로 회원가입
* parseValues 분석
  * query 스트링을 &를 기준으로 구분해 Map<String, String> 형식으로 리턴하는 유틸 함수

### 요구사항 3 - post 방식으로 회원가입
* 

### 요구사항 4 - redirect 방식으로 이동
* 

### 요구사항 5 - cookie
* 

### 요구사항 6 - stylesheet 적용
* 

### heroku 서버에 배포 후
* 