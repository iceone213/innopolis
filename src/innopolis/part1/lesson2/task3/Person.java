package innopolis.part1.lesson2.task3;

/**
 * Person
 *
 * @author Stanislav_Klevtsov
 */
public class Person implements Comparable<Person> {
    private String name;
    private int age;
    private Sex sex;

    public Person() {
        this("null", 0, Sex.MAN);
    }

    public Person(Person p) {
        this(p.name, p.age, p.sex);
    }

    public Person(String name, int age, Sex sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public Integer getAge() { return age; }
    public Sex getSex() { return sex; }

    public String getName() { return name; }
    public void setAge(int age) { this.age = age; }

    public void setSex(Sex sex) { this.sex = sex; }
    public void setName(String name) { this.name = name; }

    /**
     * Sets all fields from another Person object
     * @param p
     */
    public void setPerson(Person p) {
        this.sex = p.sex;
        this.age = p.age;
        this.name = p.name;
    }

    public String toString() {
        return this.sex + ", " + this.age + ", " + this.name;
    }

    /**
     * CompareTo
     * First compares person sex, man > woman
     * If both objects same sex, compares age, older > younger
     * If both objects same sex and age, compares names, alphabetic order
     *
     * @param p2 Person object to compare
     * @return int (0 if p1 equal p2, negative int if p1 < p2, positive int if p1 > p2)
     */
    public int compareTo(Person p2) {

        int compareSex = this.getSex().compareTo(p2.getSex());
        int compareAge = this.getAge().compareTo(p2.getAge());
        int compareName = this.getName().compareTo(p2.getName());

        if (compareAge == 0 && compareName == 0) {
            myException();
        }

        if (compareSex == 0) {
            if (compareAge == 0) {
                return compareName;
            }
            //descending order
            return -compareAge;
        }
        return compareSex;

    }

    public void myException() throws MyException {
        try {
            throw new MyException();
        } catch (MyException e) {
            Logger.e("MyException " + e.getMessage());
        }
    }

 }