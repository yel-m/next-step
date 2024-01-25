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
### 요구사항 2 - get 방식으로 회원가입
* 

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