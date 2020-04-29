package innopolis.part1.lesson2.task3;

/**
 * QuickSort
 *
 * @author Stanislav_Klevtsov
 */
public class QuickSort implements PersonSort {

    /**
     * Quick sort
     * @param personArr Person array to sort
     */
    public void sort (Person[] personArr) {
        Logger.d("---Array BEFORE Quick Sort---");
        Logger.d(personArr);

        sort(personArr, 0, personArr.length-1);

        Logger.d("---Array AFTER Quick Sort---");
        Logger.d(personArr);
    }

    /**
     * Quick sort
     * @param personArr Person array to sort
     * @param start Start index
     * @param end End index
     */
    public void sort(Person[] personArr, int start, int end){

        int partition = partition(personArr, start, end);

        if(partition-1>start) {
            sort(personArr, start, partition - 1);
        }
        if(partition+1<end) {
            sort(personArr, partition + 1, end);
        }
    }

    public int partition(Person[] personArr, int start, int end){
        Person pivot = personArr[end];

        for(int i=start; i<end; i++){
            if(personArr[i].compareTo(pivot) < 0){
                Person temp= personArr[start];
                personArr[start]=personArr[i];
                personArr[i]=temp;
                start++;
            }
        }

        Person temp = personArr[start];
        personArr[start] = pivot;
        personArr[end] = temp;

        return start;
    }

}