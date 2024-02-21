package util;

import db.DataBase;
import model.User;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;
import static util.ModelUtils.isUser;

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

    @Test
    public void validateUser() {
        // 유저 생성
        String queryParams = "userId=javajigi&password=password2&email=coin6442&name=yelim";
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryParams);
        User user = ModelUtils.createUser(parameters);
        DataBase.addUser(user);

        // 올바르게 입력했을 때
        String input1 = "userId=javajigi&password=password2&email=coin6442&name=yelim";
        Map<String, String> inputs1 = HttpRequestUtils.parseQueryString(input1);

        assertTrue(isUser(inputs1.get("userId"), inputs1.get("password")));

        // 올바르지 않게 입력했을 때
        String input2 = "userId=hihihi&password=password2&email=coin6442&name=yelim";
        Map<String, String> inputs2 = HttpRequestUtils.parseQueryString(input2);

        assertFalse(isUser(inputs2.get("userId"), inputs2.get("password")));
    }
}