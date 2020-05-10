package innopolis.part1.lesson5.dao;

import innopolis.part1.lesson5.exception.AnimalExistsException;
import innopolis.part1.lesson5.exception.PersistException;
import innopolis.part1.lesson5.model.Animal;

import java.util.UUID;

public interface AnimalDAO extends GenericDAO<Animal, UUID> {

    /**
     * Найти домашнее животное по строке имени
     * @param name
     * @return найденное живоное либо Null
     */
    Animal findAnimalByName(String name);

    /** Сортировка сохраненных элементов */
    void sortElements();

    /** Ввывод сохраненных элементов */
    void printAll();

}
