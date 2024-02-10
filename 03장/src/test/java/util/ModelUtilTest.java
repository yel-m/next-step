package util;

import model.User;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class ModelUtilTest {

    @Test
    public void createUser() {
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
}