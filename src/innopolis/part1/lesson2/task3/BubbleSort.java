package innopolis.part1.lesson2.task3;

import innopolis.part1.lesson2.task2.Logger;

import java.util.ArrayList;

/**
 * BubbleSort
 *
 * @author Stanislav_Klevtsov
 */
public class BubbleSort implements ObjectSort {

    private static final BubbleSort instance = new BubbleSort();

    public static BubbleSort getInstance() {
        return instance;
    }

    private BubbleSort() {
    }

    /**
     * Bubble sort
     *
     * @param objArr
     */
    @Override
    public void sort(Comparable[] objArr) {
        int n = objArr.length;

        Logger.d("---Array BEFORE Bubble Sort---");
        Logger.d(objArr);

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (objArr[j - 1].compareTo(objArr[j]) > 0) {
                    Comparable temp = objArr[j - 1];
                    objArr[j - 1] = objArr[j];
                    objArr[j] = temp;
                }
            }
        }

        Logger.d("---Array AFTER Bubble Sort---");
        Logger.d(objArr);

    }

}