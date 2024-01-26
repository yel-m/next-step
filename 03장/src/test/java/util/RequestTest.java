package util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;


class RequestTest {
    @Test
    void 요청_URL() {
        ArrayList<String> requestInfos = new ArrayList<>(Arrays.asList("GET /index.html HTTP/1.1", "Host : localhost:8080"));
        Assertions.assertEquals("/index.html", RequestUtil.getRequestUrl(requestInfos));
    }
}
