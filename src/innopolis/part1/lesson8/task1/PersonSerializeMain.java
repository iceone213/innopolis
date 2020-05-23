package innopolis.part1.lesson8.task1;

import innopolis.part1.lesson2.task2.Logger;
import innopolis.part1.lesson2.task3.Person;
import innopolis.part1.lesson2.task3.Sex;
import innopolis.part1.lesson6.Const;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * SerializeMain
 *
 * @author Stanislav_Klevtsov
 */
public class PersonSerializeMain {

    public static void main(String[] args) {
        Logger.p("Создаем объект Person:");
        Person p = new Person("Alex", 25, Sex.MAN);
        Logger.p(p.toString());

        Path file = Paths.get(Const.RES_FILE_PATH + "person.bin");

        if (Files.notExists(file)) {
            try {
                Files.createFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Logger.p("\nИнициализируем SerializeWorker..");
        SerializeWorker sw = new SerializeWorker();
        sw.serialize(p ,file.toString());
        Logger.p("Сериализуем объект типа Person в файл " + file.toString());

        Logger.p("Десериализуем объект из файла " + file.toString());
        Logger.p(((Person) sw.deSerialize(file.toString())).toString());

    }
}