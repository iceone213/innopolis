package innopolis.part1.lesson2.task2;

import java.util.Random;

/**
 * Utils
 *
 * @author Stanislav_Klevtsov
 */
public class Utils {

    /**
     * Generates a random integer between min (inclusive) and max (inclusive).
     *
     * @param min min value bound
     * @param max max value bound
     * @return random integer between min (inclusive) and max (inclusive).
     */
    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}