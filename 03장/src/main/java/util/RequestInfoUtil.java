package util;

import java.util.List;

public class RequestInfoUtil {
    public static String getRequestUrl(List<String> requestInfos) {
        return requestInfos.get(0).split(" ")[1];
    }
}
