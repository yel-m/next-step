# 웹 서버 실습 전 학습할 내용
```
필요하다고 생각한 것만 정리했다.
```
## 리눅스
- [리눅스 커맨드 라인 완벽 입문서](https://m.yes24.com/Goods/Detail/8208026)
  - 영어 버전 무료 다운 : https://linuxcommand.org/tlcl.php
## 크롬 개발자 도구
- [[특강] 크롬을 활용한 프론트엔드 디버깅](https://www.youtube.com/playlist?list=PLz4XWo74AOadtliOuRh0c4Szc26Zy6xzD)
## 스레드, 입출력 스트림
```
아래는 개인적으로 공부한 사이트이다.
```
# 실습 환경 세팅 및 소스코드 분석
- HTTP 웹서버의 핵심이 되는 코드는 [RequestHandler](https://github.com/yel-m/next-step/blob/9a470765691b15885a19e57e9b6f739e96349379/03%EC%9E%A5/src/main/java/webserver/RequestHandler.java) 클래스이다.
- [WebServer](https://github.com/yel-m/next-step/blob/9a470765691b15885a19e57e9b6f739e96349379/03%EC%9E%A5/src/main/java/webserver/WebServer.java) 클래스는 **웹 서버를 시작**하고, 사용자의 요청이 있을 때까지 대기 상태에 있다가 사용자 요청이 있을 경우 사용자의 요청을 RequestHandler 클래스에 위임하는 역할을 한다.


# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
* 

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