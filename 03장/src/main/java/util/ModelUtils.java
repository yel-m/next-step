package util;

import model.User;

import java.util.Map;

public class ModelUtils {
    public static void createUser(Map<String, String> parameters) {
        new User(
                parameters.get("userId"),
                parameters.get("password"),
                parameters.get("name"),
                parameters.get("email")
        );
    }
}
