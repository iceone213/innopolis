package innopolis.part1.lesson2.task3;

import java.util.ArrayList;

/**
 * PersonSort
 *
 * @author Stanislav_Klevtsov
 */
public interface ObjectSort<T extends Comparable> {

    void sort(T[] objArr);
}
