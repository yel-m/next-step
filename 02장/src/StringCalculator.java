
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    private final Pattern pattern = Pattern.compile("//(.)\n(.*)");

    int add(String text) {

        if(text == null || text.isBlank()) {
            return 0;
        }

        return sum(toInts(split(text)));

    }
    boolean isBlankOrNull(String text) {
        return text == null || text.isBlank();
    }

    String[] split(String text) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String customDelimiter = matcher.group(1);
            return matcher.group(2).split(customDelimiter);
        }
        return text.split("[:,]");
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