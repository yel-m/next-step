import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    private final Pattern pattern = Pattern.compile("//(.)\n(.*)");

    int add(String str) {

        String[] separatedExpression;

        if(str.isBlank() || str == null) {
            return 0;
        }

        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            String customDelimiter = matcher.group(1);
            separatedExpression = matcher.group(2).split(customDelimiter);
            return sum(separatedExpression);
        }
        separatedExpression = str.split("[:,]");
        return sum(separatedExpression);
    }

    int sum(String[] values) {
        int sum = 0;
        for (String value : values) {
            int number = Integer.parseInt(value);
            if(number < 0)
                throw new RuntimeException("음수를 입력해야 합니다.");
            sum += number;
        }
        return sum;
    }
}