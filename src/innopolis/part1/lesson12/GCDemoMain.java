package innopolis.part1.lesson12;

import innopolis.part1.lesson2.task2.Logger;
import sun.rmi.runtime.Log;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * GCDemoMain
 *
 * @author Stanislav_Klevtsov
 */
public class GCDemoMain {
    private static final int LOOP_COUNT = 100_000_000;

    public static void main(String[] args) throws InterruptedException, MalformedURLException {

//        demoMemoryLeak();
//        demoOutOfMemoryError();
        demoOutOfMemoryPermGen();

    }

    /**
     * Тест утечки памяти и работы GC.
     * Создает в цикле новые строки и добавляет их в List, каждый 10 элемент в цикле удаляет
     */
    static void demoMemoryLeak() throws InterruptedException {
        List<String> list = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < LOOP_COUNT; i++) {
            String str = "" + random.nextInt();
            list.add(str);
            if (i % 10 == 0) {
                Thread.sleep(1);
                list.remove(0);

            }

        }
        Logger.p(list.size());
    }

    /**
     * Создает массивы объектов в цикле.
     * Количество объектов с каждым шагом увеличевается на множитель multiplier
     */
    static void demoOutOfMemoryError() throws InterruptedException {
        Thread.sleep(10_000);

        int multiplier = 100;
        for (int i = 1; i < 50; i++) {
            Thread.sleep(1);
            Logger.p("Round " + i + " Free Memory: " + Runtime.getRuntime().freeMemory());
            int[] myIntList = new int[multiplier];

            multiplier = multiplier * 10;
        }
    }

    /**
     * Запускает цикл создания загрузчиков класса и объектов прокси.
     * Прокси сохраняются в список
     *
     * Для ускорения запускать с ключем -XX:MaxMetaspaceSize=64m
     */
    static void demoOutOfMemoryPermGen() throws InterruptedException, MalformedURLException {
        Thread.sleep(10_000);

        List<Person> list = new ArrayList<>();

        for (int i = 0; i < LOOP_COUNT; i++) {
            URL jarUrl = new URL( "file:" + i + ".jar");
            URLClassLoader loader = new URLClassLoader(new URL[] {jarUrl});

            Worker worker = new Worker("Some Guy","doing task");
            Class<?>[] interfaces = worker.getClass().getInterfaces();

            Person proxyWorker = (Person) Proxy.newProxyInstance(loader, interfaces, new PersonHandler(worker));

            proxyWorker.introduce();

            list.add(proxyWorker);  // Сохраняем ссылку на прокси
        }
    }

}