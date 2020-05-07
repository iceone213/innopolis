package innopolis.part1.lesson2.task3;

import innopolis.part1.lesson2.task2.Logger;

/**
 * PersonSortMain
 *
 * @author Stanislav_Klevtsov
 */
public class PersonSortMain {

    public static void main(String[] args) {
        //Set true to view personArr before & after sort
        //Set exceptionDebug true to view exceptions
        Logger.debug = false;
        Logger.exceptionDebug = false;

        int personCount = 1000;
        Logger.p("Person array size = " + personCount);

        //init
        PersonGenerator generator = new PersonGenerator(personCount);
        ObjectSort bubbleSort = BubbleSort.getInstance();
        ObjectSort quickSort = QuickSort.getInstance();

        //Generate Person array
        Person[] personArr = generator.generateRandomPersonArr();

        //bubble sort
        long bubleStarted = System.currentTimeMillis();
        bubbleSort.sort(personArr);
        long bubleFinished = System.currentTimeMillis() - bubleStarted;

        //quick sort
        long quickStarted = System.currentTimeMillis();
        quickSort.sort(personArr);
        long quickFinished = System.currentTimeMillis() - quickStarted;

        Logger.p("BubleSort elapsed time " + bubleFinished + " (milliseconds)");
        Logger.p("QuickSort elapsed time " + quickFinished + " (milliseconds)");
    }

    /**
     * Test initialization
     * @return Person array
     */
    public static Person[] personArrInitTest() {
        return new Person[] {
                new Person("Zeus", 2 , Sex.MAN),
                new Person("Alexis", 21, Sex.WOMAN ),
                new Person("Wendy", 21, Sex.WOMAN),
                new Person("Victoria", 21, Sex.WOMAN),
                new Person("Babe", 21, Sex.WOMAN),
                new Person("Oleg",30 , Sex.MAN),
                new Person("Kira", 15, Sex.WOMAN),
                new Person("Nataly", 20, Sex.WOMAN)
        };
    }
}