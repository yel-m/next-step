
public class StringCalculator {
    int add(String str) throws NullPointerException{
        int sum = 0;
        String[] numbers;

        if(str.substring(0, 2) == "\\" && str.substring(3, 5) == "\n") {
            String customSeparator = String.valueOf(str.charAt(2));
            numbers = str.split(customSeparator);
        } else {
            numbers = str.split("[:,]");
        }
        
        for (int i = 0; i < numbers.length; i++) {
            sum += Integer.parseInt(numbers[i]);
        }
        return sum;
    }
}