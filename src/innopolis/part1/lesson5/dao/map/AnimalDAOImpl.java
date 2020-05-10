package innopolis.part1.lesson5.dao.map;

import innopolis.part1.lesson2.task2.Logger;
import innopolis.part1.lesson5.AnimalSearch;
import innopolis.part1.lesson5.dao.AnimalDAO;
import innopolis.part1.lesson5.exception.AnimalExistsException;
import innopolis.part1.lesson5.exception.PersistException;
import innopolis.part1.lesson5.model.Animal;

import java.util.*;

/**
 * AnimalDAOImpl
 *
 * @author Stanislav_Klevtsov
 */
public class AnimalDAOImpl extends AbstractDao<Animal, UUID> implements AnimalDAO {

    public AnimalDAOImpl() {
        super(Animal.class, new LinkedHashMap<UUID, Animal>());
    }

    @Override
    public Animal save(Animal ob) throws PersistException, AnimalExistsException {
        if (elements.values().contains(ob)) {
            try {
                throw new AnimalExistsException("Duplicate exception, " +
                        ob + " already exists in catalogue");
            } catch (AnimalExistsException e) {
                Logger.e(e.getMessage());
            }
            return null;
        }
        return super.save(ob);
    }

    public Animal update(UUID id, Animal a2) throws PersistException {
        Animal animalToUpdate = super.getByPK(id);
        animalToUpdate.setAll(a2);
        return super.update(animalToUpdate);
    }

    @Override
    public Animal findAnimalByName(String name) {
        //сначала сортируем Map по имени
        sortElementsByName();

        Animal searchResult = AnimalSearch.getInstance().searchByName(elements.values(), name);

        if (searchResult != null) {
            return elements.get(searchResult.getId());
        }

//        for (Animal el:elements.values()) {
//            if (el.getName().equals(name)) {
//                return el;
//            }
//        }
        return null;
    }

    public void sortElements() {
        LinkedHashMap<UUID, Animal> sortedMap = new LinkedHashMap<UUID, Animal>();

        elements.entrySet().stream()
                .sorted(new Comparator<Map.Entry<UUID, Animal>>() {
                    @Override
                    public int compare(Map.Entry<UUID, Animal> o1, Map.Entry<UUID, Animal> o2) {
                        int compareOwner = o1.getValue().getOwner().compareTo(o2.getValue().getOwner());
                        int compareName = o1.getValue().getName().compareTo(o2.getValue().getName());
                        int compareWeight = o1.getValue().getWeight().compareTo(o2.getValue().getWeight());

                        if (compareOwner == 0) {
                            if (compareName == 0) {
                                //descending order
                                return -compareWeight;
                            }
                            return compareName;
                        }
                        return compareOwner;
                    }
                }).forEach(e -> sortedMap.put(e.getValue().getId(), e.getValue()));

        elements.clear();
        elements.putAll(sortedMap);
    }

    public void sortElementsByName() {
        LinkedHashMap<UUID, Animal> sortedMap = new LinkedHashMap<UUID, Animal>();

        elements.entrySet().stream()
                .sorted(new Comparator<Map.Entry<UUID, Animal>>() {
                    @Override
                    public int compare(Map.Entry<UUID, Animal> o1, Map.Entry<UUID, Animal> o2) {
                        return o1.getValue().getName().compareTo(o2.getValue().getName());
                    }
                }).forEach(e -> sortedMap.put(e.getValue().getId(), e.getValue()));

        elements.clear();
        elements.putAll(sortedMap);
    }

    public void printAll() {
        sortElements();

        StringBuilder builder = new StringBuilder().append('[');

        for (Animal el:elements.values())
            builder.append(el).append(",\n");

        builder.append(']');

        Logger.p(builder.toString());
    }
}