package innopolis.part1.lesson11;

import innopolis.part1.lesson2.task2.Logger;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * LambdaStreamMain
 *
 * @author Stanislav_Klevtsov
 */
public class LambdaStreamFactorialMain {

    public static void main(String[] args) {

        // Реализуем интерфес MyRandom
        MyRandom myRandom = (nMax) -> new Random().nextInt(nMax);

        // Реализуем интерфейс MyFactorial
        MyFactorial myFactorial = (n) -> {
            BigInteger result = BigInteger.ONE;

            for (int i = 2; i <= n; i++)
                result = result.multiply(new BigInteger(String.valueOf(i)));

            return result;
        };

        // Создаем экземпляр нашего класса
        LambdaStreamFactorialMain lambdaStream = new LambdaStreamFactorialMain();

        // Генерируем случайный массив
        Integer[] intArr = lambdaStream.generateIntArr(myRandom, 10, 1000);
        Logger.p("Генерируем случайный массив чисел:");
        Logger.p(intArr);

        Logger.p("Вычисляем факториалы для заданного массива чисел:");
        lambdaStream.getFactorials(myFactorial, intArr);

    }

    /**
     * Вычисляет факториалы массива чисел.
     * Для вычисления факториала передается реализации аноним. класса MyFactorial.
     * Вычисляем многопоточно с помощью Stream API.
     *
     * @param myFactorial Экземпляр аноним. класса MyFactorial
     * @param intArr Массив чисел
     */
    private void getFactorials(MyFactorial myFactorial, Integer[] intArr) {
        List<Integer> nums = Arrays.asList(intArr);

        nums.parallelStream()
                .map(myFactorial::fact) // Передаем реализацию метода аноним. класса MyFactorial fact()
                .forEach(Logger::p); // Выводим
    }

    /**
     * Генерирует и возвращает случайный массив чисел.
     * Для получения случайного числа передается реализации аноним. класса MyRandom
     *
     * @param myRandom Экземпляр аноним. класса MyRandom
     * @param count Размер массива для генерации
     * @param nMax Верхняя граница для случайной генерации числа (от 0 до nMax)
     * @return
     */
    private Integer[] generateIntArr(MyRandom myRandom, int count, int nMax) {
        Integer[] intArr = new Integer[count];

        for (int i = 0; i < intArr.length; i++) {
            intArr[i] = myRandom.myNextInt(nMax);
        }

        return intArr;
    }


    interface MyRandom {
        Integer myNextInt(int nMax);
    }

    interface MyFactorial {
        BigInteger fact(int n);
    }

}