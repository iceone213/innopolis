package innopolis.part1.lesson5;

import innopolis.part1.lesson2.task2.Logger;
import innopolis.part1.lesson2.task3.Person;
import innopolis.part1.lesson2.task3.Sex;
import innopolis.part1.lesson2.task3.names.MaleName;
import innopolis.part1.lesson5.dao.AnimalDAO;
import innopolis.part1.lesson5.dao.list.AnimalDAOImpl;
import innopolis.part1.lesson5.model.Animal;

/**
 * AnimalCatalogueMain
 *
 * @author Stanislav_Klevtsov
 */
public class AnimalCatalogueMain {

    public static void main(String[] args) {
        //Выставляем размер каталога
        int animalCount = 30;
        Logger.p("Размер католога животных = " + animalCount);

        AnimalDAO animalDAO = new AnimalDAOImpl();

        //Генерируем животных
        Logger.p("Инициализация...");
        AnimalGenerator generator = new AnimalGenerator(animalCount);
        Animal[] animalArr = generator.generateRandomPersonArr();

        //Заполняем AnimalDAO
        Logger.p("Save to AnimalDAO");
        for (int i = 0; i < animalArr.length-2; i++) {
            animalDAO.save(animalArr[i]);
        }

        //Добавляем новое животное
        Logger.p("\nДобавляем новое животное: {owner={MAN, 25, AARON}, name='Doggy', weight=57 pounds}");
        Person animalOwner = new Person(MaleName.AARON.name(), 25, Sex.MAN);

        Animal animal = new Animal();
        animal.setName("Doggy");
        animal.setWeight(57);
        animal.setOwner(animalOwner);
        animalDAO.save(animal);

        //Попытка добавить дубликат (animal == animalCopy), exception
        Logger.p("Добавляем дубликат");
        Animal animalCopy = new Animal();
        animalCopy.setName("Doggy");
        animalCopy.setWeight(57);
        animalCopy.setOwner(animalOwner);
        animalDAO.save(animalCopy);

        //Находим дом.животное по имени
        Logger.p("\nИщем животное с именем Doggy:\n" + animalDAO.findAnimalByName("Doggy"));

        //Обновление животного по ключу
        Logger.p("\nОбновляем " + animal.getName() + ", name=\"Updated Doggy\", weight=50");
        animal.setName("Updated Doggy");
        animal.setWeight(50);
        animalDAO.update(animal.getId(), animal);

        //Вывыодим отсортированный каталог
        Logger.p("\nОтсортированная каталог животных:");
        animalDAO.printAll();

    }
}