package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpRequestUtils;
import util.IOUtils;
import util.ModelUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            ArrayList<String> requestInfos = new ArrayList<>();
            String info;

            while(!"".equals(info = reader.readLine())) {
                if (info == null) { return; } // NullPointerException 처리 안해도 되나?
                requestInfos.add(info);
            }

            for(String requestInfo : requestInfos) {
                log.debug(requestInfo);
            }

            String pathParams = HttpRequestUtils.getPathParams(requestInfos);
            String queryParams = HttpRequestUtils.getQueryParams(requestInfos);
            String method = HttpRequestUtils.getMethod(requestInfos);

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body;
            if(pathParams.equals("/")) {
                pathParams = "/index.html";
            }
            if(pathParams.equals("/index.html")) {
                pathParams = "/index.html";
                body = Files.readAllBytes(new File("./webapp" + pathParams).toPath());
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
            if(pathParams.equals("/user/create")) {
                if(method.equals("GET")) {
                    pathParams = "/user/form.html";

                    if (!Strings.isNullOrEmpty(queryParams)) {
                        Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryParams);
                        ModelUtils.createUser(parameters);
                    }
                    body = Files.readAllBytes(new File("./webapp" + pathParams).toPath());
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                }

                if(method.equals("POST")) {
                    pathParams = "/index.html";
                    String data = HttpRequestUtils.getContent(requestInfos);
                    StringReader sr = new StringReader(data);
                    BufferedReader br = new BufferedReader(sr);
                    String content = IOUtils.readData(br, HttpRequestUtils.getContentLength(requestInfos));
                    Map<String, String> parameters = HttpRequestUtils.parseQueryString(content);

                    ModelUtils.createUser(parameters);
                    body = Files.readAllBytes(new File("./webapp" + pathParams).toPath());
                    response302Header(dos);
                    responseBody(dos, body);
                }
            } if(pathParams.equals("/user/login.html")) {
            }
            }else {
                body = Files.readAllBytes(new File("./webapp" + pathParams).toPath());
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
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

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
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
