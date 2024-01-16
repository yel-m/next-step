import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    int add(String str) {
        String[] separatedValues;
        Matcher matcher = Pattern.compile("//(.)\n(.*)").matcher(str);
        if (matcher.find()) {
            String customDelimiter = matcher.group(1);
            separatedValues = matcher.group(2).split(customDelimiter);
            return calculateSumFromSeparatedValues(separatedValues);
        } else {
            separatedValues = str.split("[:,]");
            return calculateSumFromSeparatedValues(separatedValues);
        }
    }

    int calculateSumFromSeparatedValues(String[] separatedValues) {
        int sum = 0;
        for (String value : separatedValues) {
            int number = changeStringToInteger(value);
            if(number < 0)
                throw new RuntimeException("음수를 입력해야 합니다.");
            else
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