package innopolis.part1.lesson8.task2;

import innopolis.part1.lesson2.task3.Person;

import java.util.Objects;
import java.util.UUID;

/**
 * AnimalPet
 *
 * @author Stanislav_Klevtsov
 */
public class Animal {

    private final Long id;
    private String name;
    private Integer weight;
    private Person owner;

    public Animal(String name, Person owner, Integer weight) {
        this.id = UUID.randomUUID().getMostSignificantBits();
        this.name = name;
        this.owner = owner;
        this.weight = weight;
    }

    public Animal() {
        this.id = UUID.randomUUID().getMostSignificantBits();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setAll(Animal a2) {
        this.name = a2.name;
        this.owner = a2.owner;
        this.weight = a2.weight;
    }

    /**
     * CompareTo
     * Сначала сравнивает домашних животных по хозяину.
     * Если у обоих один и тот же хозяин, сравнивает по имени, в алфавитном порядке.
     * Если у обоих один и тот же хозяин и имя, сравнивает по весу, в порядке убывания.
     *
     * @param a2 домашнее животное для сравнения
     * @return int (0 если a1 == a2, негативное число если a1 < a2, позитивное число если a1 > a2)
     */
    public int compareTo(Animal a2) {

        int compareOwner = this.getOwner().compareTo(a2.getOwner());
        int compareName = this.getName().compareTo(a2.getName());
        int compareWeight = this.getWeight().compareTo(a2.getWeight());

        if (compareOwner == 0) {
            if (compareName == 0) {
                //descending order
                return -compareWeight;
            }
            return compareName;
        }
        return compareOwner;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(name, animal.name) &&
                Objects.equals(owner, animal.owner) &&
                Objects.equals(weight, animal.weight);
    }

    @Override
    public int hashCode() {
        return 29 * Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", owner={" + owner.getSex() + ", " + owner.getAge() + ", " + owner.getName() + "}" +
                ", name='" + name + '\'' +
                ", weight=" + weight + " pounds" +
                '}';
    }

    public int compareTo(Object o) {
        return 0;
    }

}