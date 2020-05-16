package innopolis.part1.lesson7;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * FactorialMain
 *
 * @author Stanislav_Klevtsov
 */
public class FactorialMain {
    // Returns Factorial of N


    public static void main(String args[]) {
        int count = 100;
        int minValue = 1;
        int maxValue = 1000;

        Integer[] numbers = MathUtils.generateRandomIntArr(count, minValue, maxValue);
        ArrayList<Thread> threads = new ArrayList<>();

        for (Integer number : numbers) {

            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(number + "!:" + MathUtils.factorial(number));
                }
            }));
        }



    }
}