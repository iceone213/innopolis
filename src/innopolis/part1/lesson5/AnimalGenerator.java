package innopolis.part1.lesson5;

import innopolis.part1.lesson2.task2.Logger;
import innopolis.part1.lesson2.task2.Utils;
import innopolis.part1.lesson2.task3.Person;
import innopolis.part1.lesson2.task3.PersonGenerator;
import innopolis.part1.lesson2.task3.Sex;
import innopolis.part1.lesson2.task3.names.FemaleName;
import innopolis.part1.lesson2.task3.names.MaleName;
import innopolis.part1.lesson5.enums.AnimalNames;
import innopolis.part1.lesson5.model.Animal;

import javax.rmi.CORBA.Util;
import java.util.Random;

/**
 * PersonGenerator
 *
 * @author Stanislav_Klevtsov
 */
public class AnimalGenerator {
    private final int MIN_NAME = 0;
    private final int MAX_NAME = AnimalNames.values().length-1;

    private final int MIN_WEIGHT = 5;
    private final int MAX_WEIGHT = 100;

    private int animalCount;
    private Animal[] animalArr;

    public AnimalGenerator(int count) {
        this.animalCount = count;
        animalArr = new Animal[count];

        Logger.p("AnimalGenerator created");
    }

    /**
     * Генерируем новый AnimalArr
     */
    public Animal[] generateRandomPersonArr() {

        PersonGenerator generator = new PersonGenerator(animalCount);
        Person[] personArr = generator.generateRandomPersonArr();

        for (int i = 0; i < animalArr.length; i++) {
            int randomWeight = Utils.getRandomNumberInRange(MIN_WEIGHT, MAX_WEIGHT);
            int randomName = Utils.getRandomNumberInRange(MIN_NAME, MAX_NAME);

            animalArr[i] = new Animal(
                    AnimalNames.values()[randomName].name(),
                    personArr[i],
                    randomWeight
            );

        }

        Logger.p("Generated new animalArr");
        return animalArr;
    }

}