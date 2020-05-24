package innopolis.part1.lesson9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * MyClassLoader
 *
 * @author Stanislav_Klevtsov
 */
public class MyClassLoader extends ClassLoader {

    /**
     * Загружает SomeClass
     *
     * @param name Имя класса
     * @return Class
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if ("innopolis.part1.lesson9.SomeClass".equals(name)) {
            return findClass(name);
        }
        return super.loadClass(name);
    }

    /**
     * Поиск класса
     * Считывает из файла .class все байты и возвращает класс
     *
     * @param name Имя класса
     * @return Class
     */
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        if ("innopolis.part1.lesson9.SomeClass".equals(name)) {
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(".\\src\\innopolis\\part1\\lesson9\\SomeClass.class"));
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return super.findClass(name);
    }

}