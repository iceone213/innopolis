package innopolis.part1.lesson2.task3;

/**
 * ObjectSort
 *
 * @author Stanislav_Klevtsov
 */
public interface ObjectSort<T extends Comparable<T>> {

    void sort(T[] objArr);

}
