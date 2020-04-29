package innopolis.part1.lesson2.task3;

/**
 * BubbleSort
 *
 * @author Stanislav_Klevtsov
 */
public class BubbleSort implements PersonSort {

    /**
     * Bubble sort
     * @param personArr Person array to sort
     */
    public void sort(Person[] personArr) {
        int n = personArr.length;
        Person temp = new Person();

        Logger.d("---Array BEFORE Bubble Sort---");
        Logger.d(personArr);

        for(int i=0; i < n; i++) {
            for(int j=1; j < (n-i); j++) {
                if (personArr[j-1].compareTo(personArr[j]) > 0) {
                    swap(temp, personArr[j-1], personArr[j]);
                }
            }
        }

        Logger.d("---Array AFTER Bubble Sort---");
        Logger.d(personArr);

    }

    /**
     * Swap person
     * @param temp temporary obj
     * @param p1 person #1
     * @param p2 person #2
     */
    private void swap(Person temp, Person p1, Person p2) {
        temp.setPerson(p1);
        p1.setPerson(p2);
        p2.setPerson(temp);
    }

}