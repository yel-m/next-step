package util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import model.User;
import org.junit.Test;

import util.HttpRequestUtils.Pair;

public class HttpRequestUtilsTest {

    @Test
    public void getMethod() {
        ArrayList<String> requestInfos = new ArrayList<>(Arrays.asList("GET /index.html HTTP/1.1", "Host : localhost:8080"));
        assertEquals("GET", HttpRequestUtils.getMethod(requestInfos));
    }
    @Test
    public void getPathParams() {
        ArrayList<String> requestInfos = new ArrayList<>(Arrays.asList("GET /index.html HTTP/1.1", "Host : localhost:8080"));
        assertEquals("/index.html", HttpRequestUtils.getPathParams(requestInfos));
    }

    @Test
    public void getQueryParams() {
        ArrayList<String> requestInfos = new ArrayList<>(Arrays.asList("GET /index.html?userId=javajigi HTTP/1.1", "Host : localhost:8080"));
        assertEquals("userId=javajigi", HttpRequestUtils.getQueryParams(requestInfos));
    }

    @Test
    public void getContentLength() {

        ArrayList<String> requestInfos = new ArrayList<>(
                Arrays.asList(
                        "POST /user/create HTTP/1.1",
                        "Host : localhost:8080",
                        "Connection: keep-alive",
                        "Content-length: 59",
                        "Content-Type: application/x-www-form-urlencoded",
                        "Accept: */*"
                ));
        assertEquals(59, HttpRequestUtils.getContentLength(requestInfos));
    }

    @Test
    public void getUserInfo() {
        String queryParams = "userId=javajigi&password=password2&email=coin6442&name=yelim";
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryParams);
        User user = new User(
                parameters.get("userId"),
                parameters.get("password"),
                parameters.get("name"),
                parameters.get("email")
        );
        assertEquals("javajigi", user.getUserId());
        assertEquals("password2", user.getPassword());
        assertEquals("coin6442", user.getEmail());
        assertEquals("yelim", user.getName());
    }

    @Test
    public void parseQueryString() {
        String queryString = "userId=javajigi";
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
        assertThat(parameters.get("userId"), is("javajigi"));
        assertThat(parameters.get("password"), is(nullValue()));

        queryString = "userId=javajigi&password=password2";
        parameters = HttpRequestUtils.parseQueryString(queryString);
        assertThat(parameters.get("userId"), is("javajigi"));
        assertThat(parameters.get("password"), is("password2"));
    }

    @Test
    public void parseQueryString_null() {
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(null);
        assertThat(parameters.isEmpty(), is(true));

        parameters = HttpRequestUtils.parseQueryString("");
        assertThat(parameters.isEmpty(), is(true));

        parameters = HttpRequestUtils.parseQueryString(" ");
        assertThat(parameters.isEmpty(), is(true));
    }

    @Test
    public void parseQueryString_invalid() {
        String queryString = "userId=javajigi&password";
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
        assertThat(parameters.get("userId"), is("javajigi"));
        assertThat(parameters.get("password"), is(nullValue()));
    }

    @Test
    public void parseCookies() {
        String cookies = "logined=true; JSessionId=1234";
        Map<String, String> parameters = HttpRequestUtils.parseCookies(cookies);
        assertThat(parameters.get("logined"), is("true"));
        assertThat(parameters.get("JSessionId"), is("1234"));
        assertThat(parameters.get("session"), is(nullValue()));
    }

    @Test
    public void getKeyValue() throws Exception {
        Pair pair = HttpRequestUtils.getKeyValue("userId=javajigi", "=");
        assertThat(pair, is(new Pair("userId", "javajigi")));
    }

    @Test
    public void getKeyValue_invalid() throws Exception {
        Pair pair = HttpRequestUtils.getKeyValue("userId", "=");
        assertThat(pair, is(nullValue()));
    }

    @Test
    public void parseHeader() throws Exception {
        String header = "Content-Length: 59";
        Pair pair = HttpRequestUtils.parseHeader(header);
        assertThat(pair, is(new Pair("Content-Length", "59")));
    }
}
