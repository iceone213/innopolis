package innopolis.part1.lesson2.task3;

import innopolis.part1.lesson2.task2.Logger;

/**
 * QuickSort
 *
 * @author Stanislav_Klevtsov
 */
public class QuickSort implements ObjectSort {

    private static final QuickSort instance = new QuickSort();

    public static QuickSort getInstance() { return instance; }

    private QuickSort() {
    }

    /**
     * Quick sort
     *
     * @param objArr Object array to sort
     */
    public void sort(Comparable[] objArr) {
        Logger.d("---Array BEFORE Quick Sort---");
        Logger.d(objArr);

        sort(objArr, 0, objArr.length - 1);

        Logger.d("---Array AFTER Quick Sort---");
        Logger.d(objArr);
    }

    /**
     * Quick sort
     *
     * @param objArr Object array to sort
     * @param start     Start index
     * @param end       End index
     */
    public void sort(Comparable[] objArr, int start, int end) {

        int partition = partition(objArr, start, end);

        if (partition - 1 > start) {
            sort(objArr, start, partition - 1);
        }
        if (partition + 1 < end) {
            sort(objArr, partition + 1, end);
        }
    }

    public int partition(Comparable[] objArr, int start, int end) {
        Comparable pivot = objArr[end];

        for (int i = start; i < end; i++) {
            if (objArr[i].compareTo(pivot) < 0) {
                Comparable temp = objArr[start];
                objArr[start] = objArr[i];
                objArr[i] = temp;
                start++;
            }
        }

        Comparable temp = objArr[start];
        objArr[start] = pivot;
        objArr[end] = temp;

        return start;
    }

}