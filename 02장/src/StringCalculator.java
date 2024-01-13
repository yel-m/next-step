public class StringCalculator {
    int add(String str) throws NullPointerException {
        int sum = 0;
        String[] separatedStrings;
        if ("//".equals(str.substring(0, 2)) && "\n".equals(str.substring(3, 4))) {
            String customSeparator = String.valueOf(str.charAt(2));
            str = str.replace("//", "").replace("\n", "");
            separatedStrings = str.split(customSeparator);
            for (String separatedString : separatedStrings) {
                sum += changeStingToInteger(separatedString);
            }
        } else {
            separatedStrings = str.split("[:,]");
            for (String separatedString : separatedStrings) {
                sum += changeStingToInteger(separatedString);
            }
        }

        return sum;
    }

    Integer changeStingToInteger(String str) {
        if(str.isBlank() || str == null) {
            return 0;
        } else {
            return Integer.parseInt(str);
        }
    }
}