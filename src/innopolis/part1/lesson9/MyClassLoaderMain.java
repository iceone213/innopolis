package innopolis.part1.lesson9;

import innopolis.part1.lesson6.Const;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * MyClassLoaderMain
 *
 * @author Stanislav_Klevtsov
 */
public class MyClassLoaderMain {

    public static void main(String[] args) {

        // Получаем с консоли код метода doWork
        String code = getCode();

        Path file = Paths.get(".\\src\\innopolis\\part1\\lesson9\\SomeClass.java");
        Path tmp = null;

        try {
            // Создаем временный файл для объединения файла SomeClass.java с пустым методом
            // и пользовательского кода
            tmp = Files.createTempFile(Paths.get(Const.RES_FILE_PATH), "lesson9_", ".tmp");
            writeTempFile(code, file, tmp);
            // Перезаписываем SomeClass
            writeInJavaFile(file, tmp);
            // Удаляем временный файл
            Files.delete(tmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); // Компилируем

        compiler.run(null, null, null, file.toString());

        useCustomClassLoader(); // Используем кастомный загрузчик для загрузки класса и запуска метода

    }

    /**
     * Получает пользовательский код метода doWork
     * Для упрощения тестирования выполнено считывание строк из файла method.txt
     *
     * @return StringBuilder, содержащий пользовательский код
     */
    private static String getCode() {
        System.out.println("Введите текст программы:");
        StringBuilder code = new StringBuilder();
        String input;
        InputStream in = System.in;

        // Подмена источника
        try {
            System.setIn(new FileInputStream(".\\src\\innopolis\\part1\\lesson9\\method.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                input = reader.readLine();
                if (input == null || input.equals("")) {
                    break;
                }
                code.append(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setIn(in); // Возвращаем источник консольного ввода

        return code.toString();
    }

    /**
     * Объединяет пользовательский код с телом класса SomeClass во временном файле
     *
     * @param code StringBuilder - полученный код метода doWork
     * @param file Файл SomeClass.java
     * @param tmp Временный файл
     */
    private static void writeTempFile(String code, Path file, Path tmp) {
        long length = file.toFile().length();
        char[] buffer = new char[(int) length];
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tmp.toFile()));
             BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {
            if (reader.ready()) {
                reader.read(buffer);
            }
            int someClassDoWorkMethodBytesPos = 171;

            writer.write(buffer, 0, someClassDoWorkMethodBytesPos);
            writer.write(code);
            writer.write(buffer, someClassDoWorkMethodBytesPos, (int) (length - someClassDoWorkMethodBytesPos));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Записывает полный код класса из временного файла в файл SomeClass.java
     *
     * @param file Файл SomeClass.java
     * @param tmp Временный файл
     */
    private static void writeInJavaFile(Path file, Path tmp) {
        try (BufferedReader reader = new BufferedReader(new FileReader(tmp.toFile()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile()))) {
            while (reader.ready()) {
                writer.write(reader.readLine() + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Использует кастомный загрузчик для загрузки класса и вызывает на исполнение
     * метод doWork
     */
    private static void useCustomClassLoader() {
        MyClassLoader classLoader = new MyClassLoader();
        try {
            Class<?> cl = classLoader.loadClass("innopolis.part1.lesson9.SomeClass");
            Object o = cl.newInstance();
            Method doWork = cl.getMethod("doWork");
            doWork.invoke(o);

        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}