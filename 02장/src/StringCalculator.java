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
            return sum(toInts(separatedExpression));
        }
        separatedExpression = str.split("[:,]");
        return sum(toInts(separatedExpression));
    }

    int[] toInts(String[] values) {
        int[] numbers = new int[values.length];
        for(int i = 0; i < values.length; i++) {
            numbers[i] = Integer.parseInt(values[i]);
        }
        return numbers;
    }

    int sum(int[] numbers) {
        int sum = 0;
        for (int number : numbers) {
            if(number < 0)
                throw new RuntimeException("음수를 입력해야 합니다.");
            sum += number;
        }
        return sum;
    }


}