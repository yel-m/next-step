public class StringCalculator {
    int add(String str) {
        if (str.indexOf("\n") - str.indexOf("//") == 3) {
            String customSeparator = String.valueOf(str.charAt(str.indexOf("\n") - str.indexOf("//") - 1));
            str = str.substring(str.indexOf("\n") + 1).replace(customSeparator, ",");
            return calculateSumWithDelimiter(str);
        } else {
            return calculateSumWithDelimiter(str);
        }
    }

    int calculateSumWithDelimiter(String str) {
        int sum = 0;
        String[] separatedStrings = str.split("[:,]");
        for (String separatedString : separatedStrings) {
            int number = changeStringToInteger(separatedString);
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