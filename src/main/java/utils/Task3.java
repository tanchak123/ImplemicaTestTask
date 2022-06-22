package utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Task3 {

    public static void main(String[] args) {

        String a = "93 326 215 443 944 152 681 699 238 856 266 700 490 715 968 264 381 621 468 592".replaceAll(" ", "");

        int sum = 0;
        for (char b : a.toCharArray()) {
            sum += Character.getNumericValue(b);
            System.out.println(sum);
        }

        System.out.println(sum);
        a = "963 895 217 599 993 229 915 608 941 463 976 156 518 286 253 697 920 827 223 758 251 185 210 916 864 000 000 000 000 000 000 000 000".replaceAll(" ", "");

        for (char b : a.toCharArray()) {
            sum += Character.getNumericValue(b);
            System.out.println(sum);
        }

        List<Long> currentNumbers = List.of(100L);

        for (Long currentNumber : currentNumbers) System.out.println(calculateSumOfDigits(calculateFactorial(new BigInteger("1"), currentNumber).toString()));
    }

    public static BigInteger calculateFactorial(BigInteger startNumber, Long number) {
        System.out.println(startNumber.toString());
        startNumber = startNumber.multiply(new BigInteger(number.toString()));
        return number > 1 ? calculateFactorial(startNumber,  + number - 1) : startNumber;
    }

    private static int calculateSumOfDigits(String number) {
        int sum = 0;
        for (char character : number.toCharArray()) {
            sum += Character.getNumericValue(character);
        }

        return sum;
    }
}
