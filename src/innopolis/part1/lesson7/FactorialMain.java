package innopolis.part1.lesson7;

import innopolis.part1.lesson2.task2.Logger;
import innopolis.part1.lesson2.task2.Utils;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;

/**
 * FactorialMain
 *
 * @author Stanislav_Klevtsov
 */
public class FactorialMain {

    public static void main(String args[]) throws ExecutionException, InterruptedException {
        // Для наглядности работы с потоками ставим true
        Logger.debug = false;

        FactorWorker fw = new FactorWorker();

        // Интересные комбинации для тестов:
        // 100000, 0, 1000 - быстрее вычисление потоками
        // 1000, 0, 10000 - быстрее вычисление с сохранением
        Integer[] intArr = Utils.generateRandomIntArr(100, 0, 10000);

        Logger.p("Вычисляем факториалы исходного массива чисел:");
        Logger.p(intArr);

        Logger.d("БЕЗ сохранения значений:");
        long factStarted = System.currentTimeMillis();
        for (Integer i : intArr)
            fw.factorial(i);
        long factFinished = System.currentTimeMillis() - factStarted;

        Logger.d("С сохранением значений:");
        long nearestFactStarted = System.currentTimeMillis();
        fw.factorialsFromIntArr(intArr);
        long nearestFactFinished = System.currentTimeMillis() - nearestFactStarted;

        Logger.d("В потоках:");
        long threadFactStarted = System.currentTimeMillis();
        fw.factorialsWithThreads(intArr, 2);
        long threadFactFinished = System.currentTimeMillis() - threadFactStarted;

        Logger.p("БЕЗ сохранения значений: " + factFinished + " (ms)");
        Logger.p("С сохранением значений: " + nearestFactFinished + " (ms)");
        Logger.p("В потоках: " + threadFactFinished + " (ms)");

    }

}