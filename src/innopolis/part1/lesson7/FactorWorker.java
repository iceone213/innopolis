package innopolis.part1.lesson7;

import innopolis.part1.lesson2.task2.Logger;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * FactorUtils
 *
 * @author Stanislav_Klevtsov
 */
public class FactorWorker {

    private Map<Integer, BigInteger> factorMap;

    /**
     * Вычисляет факториалы массива целых чисел.
     * Сохраняет промежуточные значения для улучшения производительности.
     *
     * @param intArr Массив целых чисел.
     * @return Ассоциативный массив <Целое число, Вычесленный факториал>
     */
    public Map<Integer, BigInteger> factorialsFromIntArr(Integer[] intArr) {

        factorMap = new ConcurrentHashMap<>();

        // Сортируем
        // чтобы вычислять факториалы от ближайшего меньшего
        Arrays.sort(intArr);

        for (int i = 0; i < intArr.length - 1; i++) {

            // Первый факториал вычисляем 1 .. intArr[0]
            // далее, вычисляем факториал от ближайшего intArr[i-1] .. intArr[i]
            if (i == 0) {
                factorMap.put(
                        intArr[i],
                        factorial(intArr[i])
                );
            } else {
                factorMap.put(
                        intArr[i],
                        factorialFromNearest(intArr[i], intArr[i - 1], factorMap.get(intArr[i - 1]))
                );
            }

        }

        return factorMap;
    }

    /**
     * Вычисляет факториалы массива целых чисел.
     * Сохраняет промежуточные значения для улучшения производительности.
     * Расспаралеливает задчу использую пул потоков.
     *
     * @param intArr      Массив целых чисел.
     * @param threadCount Кол-во потоков, для вычисления.
     * @return Ассоциативный массив <Целое число, Вычесленный факториал>
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Map<Integer, BigInteger> factorialsWithThreads(Integer[] intArr, int threadCount) throws ExecutionException, InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        factorMap = new ConcurrentHashMap<>();

        Arrays.sort(intArr);

        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < intArr.length - 1; i++) {
            final int j = i;
            futures.add(
                    CompletableFuture.supplyAsync(
                            () -> {
                                if (j == 0)
                                    factorMap.put(intArr[j], factorial(intArr[j]));
                                else
                                    factorMap.put(
                                            intArr[j],
                                            factorialFromNearest(intArr[j], intArr[j - 1], factorMap.get(intArr[j - 1]))
                                    );

                                return factorMap.get(intArr[j]);
                            },
                            threadPool
                    ));
        }

        for (Future future : futures) {
            future.get();
        }

        threadPool.shutdown();

        return factorMap;
    }


    /**
     * Вычисляет факториал числа N.
     * В расчете использует ближайшее число (nearestN), факториал которого уже вычеслен (nearestFact).
     *
     * @param N           Число, факториал которого необходимо вычислить.
     * @param nearestN    Ближайшее число, факториал которого уже вычеслен.
     * @param nearestFact Факториал от ближайшего числа.
     * @return Факториал числа N
     */
    public BigInteger factorialFromNearest(Integer N, Integer nearestN, BigInteger nearestFact) {

        // Для потоков
        // проверяем вычеслили ли предыдущее значение factorialMap
        // иначе, вычисляем факториал 1 .. N
        if (nearestFact != null) {
            BigInteger f = nearestFact;

            // Вычисляем факториал nearestN+1, nearestN+2, ...N
            for (int i = nearestN + 1; i <= N; i++)
                // нерекурсивный метод
                f = f.multiply(BigInteger.valueOf(i));

            Logger.d(Thread.currentThread().getName() + ": fact " + N + " nearest " + nearestN);

            return f;
        }

        return factorial(N);
    }

    /**
     * Вычисляет факториал числа N.
     *
     * @param N Число, факториал которого необходимо вычислить.
     * @return Факториал числа N
     */
    public BigInteger factorial(Integer N) {
        BigInteger f = BigInteger.ONE;

        // Вычисляем факториал 2, 3, ...N
        for (int i = 2; i <= N; i++)
            // нерекурсивный метод
            f = f.multiply(BigInteger.valueOf(i));

        Logger.d(Thread.currentThread().getName() + ": fact " + N);

        return f;
    }

}