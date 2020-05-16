package innopolis.part1.lesson7;

import innopolis.part1.lesson2.task2.Logger;
import innopolis.part1.lesson2.task2.Utils;

import java.math.BigInteger;

/**
 * FactorUtils
 *
 * @author Stanislav_Klevtsov
 */
public class MathUtils {

    static BigInteger factorial(int N) {
        // Initialize
        BigInteger f = BigInteger.ONE;

        // Multiply f with 2, 3, ...N
        for (int i = 2; i <= N; i++)
            f = f.multiply(BigInteger.valueOf(i)); // Not recursive method by default

        return f;
    }

    static Integer[] generateRandomIntArr(int count, int minValue, int maxValue) {
        Integer[] numbers = new Integer[count];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Utils.randomInt(minValue, maxValue);
        }

        return numbers;
    }
}