package innopolis.part1.lesson2.task2;

import java.util.Random;

/**
 * RandomNumbersGenerator
 *
 * @author Stanislav_Klevtsov
 */
public class RandomNumbersGenerator {
    private int nminValue = 0;
    private int nmaxValue = 10000;
    private int n;
    private Integer[] randomNumbersArr;

    public RandomNumbersGenerator(int n) {
        this.n = n;
        randomNumbersArr = new Integer[n];

        Logger.p("RandomNumbersGenerator created");
    }

    public RandomNumbersGenerator(int n, int nminValue, int nmaxValue) {
        this(n);
        this.nminValue = nminValue;
        this.nmaxValue = nmaxValue;
    }

    /**
     * Generates new randomNumbersArr
     */
    public void generateNewRandomNumbersArr() {
        for (int i = 0; i < randomNumbersArr.length; i++) {
            randomNumbersArr[i] = getRandomNumberInRange(nminValue, nmaxValue);
        }

        Logger.p("Generated new randomNumbersArr");
        Logger.d(randomNumbersArr);
    }

    /**
     * Prints numbers from randomNumbersArr
     */
    public void printRandomNumbersArr() {
        Logger.p("---Printing number if task2.condition---");

        if (randomNumbersArr != null && randomNumbersArr.length > 0) {

            for (int i = 0; i < randomNumbersArr.length; i++) {

                //Throws exception if number is negative
                try {
                    if (randomNumbersArr[i] < 0)
                        throw new IllegalArgumentException();
                } catch (IllegalArgumentException e) {
                    Logger.exceptionDebug(
                            "IllegalArgumentException. " + randomNumbersArr[i] + " don't have square root"
                    );
                    continue;
                }

                int sqrt = (int) Math.sqrt(randomNumbersArr[i]);
                int sqrt2 = (int) Math.pow(sqrt, 2);

                if (sqrt2 == randomNumbersArr[i]) {
                    Logger.p(randomNumbersArr[i] + ", sqrt= " + sqrt + ", (" + sqrt + "^2)= " + sqrt2);
                }

            }
            return;
        }

        Logger.p("randomNumbersArr is empty or null! Please generate new randomNumbersArr first");
    }

    /**
     * Generates a random integer between min (inclusive) and max (inclusive).
     *
     * @param min min value bound
     * @param max max value bound
     * @return random integer between min (inclusive) and max (inclusive).
     */
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


}