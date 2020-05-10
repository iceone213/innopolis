package innopolis.part1.lesson5;

import innopolis.part1.lesson2.task2.Logger;
import innopolis.part1.lesson5.model.Animal;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * BinaryArrayListSearch
 *
 * @author Stanislav_Klevtsov
 */
public class AnimalSearch<T extends Comparable<T>> {

    public static AnimalSearch getInstance() { return instance; }

    /** Метод доступа к единственному экзепляру класса */
    private static final AnimalSearch instance = new AnimalSearch();

    private AnimalSearch() {
    }

    /**
     * Бинарный поиск
     * На вход должен получать отсортированный список.
     * Берем необходимый elementName, сравниваем с серединой списка, уходим влево/вправо, повторяемю
     *
     * @param collection    ArrayList из Animal для поиска
     * @param elementName   Имя по которому ищем элемент в ArrayList
     * @return              Возвращает экземпляр в коллекции, если нет null
     */
    public Animal searchByName(Collection collection, String elementName)  {

        ArrayList<Animal> arrayList = new ArrayList<Animal>(collection);

        int firstIndex = 0;
        int lastIndex = collection.size() - 1;

        // условие прекращения (элемент не представлен)
        while(firstIndex <= lastIndex) {
            int middleIndex = (firstIndex + lastIndex) / 2;
            // если средний элемент - целевой элемент, вернуть его индекс

            if (arrayList.get(middleIndex).getName().compareTo(elementName) == 0) {
                return arrayList.get(middleIndex);
            }

            // если средний элемент меньше
            // направляем наш индекс в middle+1, убирая первую часть из рассмотрения
            else if (arrayList.get(middleIndex).getName().compareTo(elementName)  < 0)
                firstIndex = middleIndex + 1;

                // если средний элемент больше
                // направляем наш индекс в middle-1, убирая вторую часть из рассмотрения
            else if (arrayList.get(middleIndex).getName().compareTo(elementName)  > 0)
                lastIndex = middleIndex - 1;

        }
        return null;
    }

}