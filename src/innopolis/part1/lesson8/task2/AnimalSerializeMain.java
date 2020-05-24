package innopolis.part1.lesson8.task2;

import innopolis.part1.lesson2.task2.Logger;
import innopolis.part1.lesson2.task3.Person;
import innopolis.part1.lesson2.task3.Sex;
import innopolis.part1.lesson6.Const;
import innopolis.part1.lesson8.task1.Serialize;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * AnimalSerializeMain
 *
 * @author Stanislav_Klevtsov
 */
public class AnimalSerializeMain {

    public static void main(String[] args) {
        Logger.p("Создаем объект Animal с вложенным объектом Person:");
        Person alex = new Person("Alex", 25, Sex.MAN);
        Animal doggy = new Animal("Doggy", alex, 50);
        Logger.p(doggy.toString());

        Path file = Paths.get(Const.RES_FILE_PATH + "animal.bin");
        if (Files.notExists(file)) {
            try {
                Files.createFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Logger.p("\nИнициализируем RecursiveSerializeWorker..");
        Serialize sw = new RecursiveSerializeWorker();
        sw.serialize(doggy ,file.toString());
        Logger.p("Сериализуем объект типа Animal в файл " + file.toString());

        Logger.p("Десериализуем объект из файла " + file.toString());
        Animal animal = (Animal) sw.deSerialize(file.toString());
        Logger.p(animal.toString());
    }
}