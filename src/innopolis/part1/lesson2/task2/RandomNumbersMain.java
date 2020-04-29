package innopolis.part1.lesson2.task2;

/**
 * GenerateRandomNumbers
 * Program that generates N random numbers and prints them if task2.condition met.
 *
 * @author Stanislav_Klevtsov
 */
public class RandomNumbersMain {

    public static void main(String[] args) {
        //Set debug true to view generated randomNumbersArr
        //Set exceptionDebug true to view exceptions
        Logger.debug = false;
        Logger.exceptionDebug = true;

        int n = 1000;
        int nminValue = -100;
        int nmaxValue = 1000;
        Logger.p("n = " + n + ",\nnminValue = " + nminValue +",\nnmaxValue = " + nmaxValue);

        RandomNumbersGenerator generator = new RandomNumbersGenerator(n, nminValue, nmaxValue);
        generator.generateNewRandomNumbersArr();
        generator.printRandomNumbersArr();
    }

}