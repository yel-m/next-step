package util;

import model.User;

import java.util.Map;

import static db.DataBase.findUserById;

public class ModelUtils {
    public static User createUser(Map<String, String> parameters) {
        return new User(
                parameters.get("userId"),
                parameters.get("password"),
                parameters.get("name"),
                parameters.get("email")
        );
    }

    public static boolean isUser(String userId, String password) {
        User user = findUserById(userId);
        return user != null && user.getPassword().equals(password);
    }
}
