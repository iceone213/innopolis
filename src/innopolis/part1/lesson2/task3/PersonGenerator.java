package innopolis.part1.lesson2.task3;

import innopolis.part1.lesson2.task2.Logger;
import innopolis.part1.lesson2.task2.Utils;
import innopolis.part1.lesson2.task3.names.FemaleName;
import innopolis.part1.lesson2.task3.names.MaleName;

/**
 * PersonGenerator
 *
 * @author Stanislav_Klevtsov
 */
public class PersonGenerator {
    private final int MIN_MALE_NAME = 0;
    private final int MAX_MALE_NAME = MaleName.values().length-1;
    private final int MIN_FEMALE_NAME = 0;
    private final int MAX_FEMALE_NAME = FemaleName.values().length-1;

    private final int MIN_SEX = 0;
    private final int MAX_SEX = Sex.values().length-1;

    private final int MIN_AGE = 0;
    private final int MAX_AGE = 100;

    private int personCount;
    private Person[] personArr;

    public PersonGenerator(int count) {
        this.personCount = count;
        personArr = new Person[count];

        Logger.p("PersonGenerator created");
    }

    /**
     * Generates new personArr
     */
    public Person[] generateRandomPersonArr() {
        for (int i = 0; i < personArr.length; i++) {
            int randomSex = Utils.randomInt(MIN_SEX, MAX_SEX);
            int randomAge = Utils.randomInt(MIN_AGE, MAX_AGE);

            int randomName;

            if (Sex.values()[randomSex] == Sex.MAN) {
                randomName = Utils.randomInt(MIN_MALE_NAME, MAX_MALE_NAME);

                personArr[i] = new Person(
                        MaleName.values()[randomName].name(),
                        randomAge,
                        Sex.values()[randomSex]
                );

            } else {
                randomName = Utils.randomInt(MIN_FEMALE_NAME, MAX_FEMALE_NAME);

                personArr[i] = new Person(
                        FemaleName.values()[randomName].name(),
                        randomAge,
                        Sex.values()[randomSex]
                );
            }

        }

        Logger.p("Generated new personArr");
        return personArr;
    }

}