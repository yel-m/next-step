import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    private final Pattern pattern = Pattern.compile("//(.)\n(.*)");

    int add(String str) {
        String[] separatedExpression;
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            String customDelimiter = matcher.group(1);
            separatedExpression = matcher.group(2).split(customDelimiter);
            return calculateSumFromSeparatedValues(separatedExpression);
        }
        separatedExpression = str.split("[:,]");
        return calculateSumFromSeparatedValues(separatedExpression);
    }

    int calculateSumFromSeparatedValues(String[] separatedExpression) {
        int sum = 0;
        for (String expression : separatedExpression) {
            int number = changeStringToInteger(expression);
            if(number < 0)
                throw new RuntimeException("음수를 입력해야 합니다.");
            sum += number;
        }
        return sum;
    }

    int changeStringToInteger(String str) {
        if(str.isBlank() || str == null) {
            return 0;
        } else {
            return Integer.parseInt(str);
        }
    }
}