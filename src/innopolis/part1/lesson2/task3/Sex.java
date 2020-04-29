package innopolis.part1.lesson2.task3;

/**
 * Sex
 *
 * @author Stanislav_Klevtsov
 */
public enum Sex {
    MAN("MAN", 1),
    WOMAN("WOMAN", 2);

    private final String sex;
    private final int position;

    Sex(final String sex, final int position) {
        this.sex = sex;
        this.position = position;
    }

    public String toString() {
        return sex;
    }

    public int getPos() {
        return position;
    }
}