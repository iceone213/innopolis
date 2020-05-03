package innopolis.part1.lesson3.box;

import innopolis.part1.lesson3.Logger;

import java.util.*;

/**
 * ObjectBox
 *
 * @author Stanislav_Klevtsov
 */
public class ObjectBox {
    protected final String name;
    protected Set<Object> objSet = new HashSet<>();

    public String getName() { return name; }

    /**
     * Constructor
     * Sets the name & populates objSet from Object[].
     * @param name
     * @param objArr
     */
    public ObjectBox(String name, Object[] objArr) {
        this.name = name;
        objSet = new HashSet<>(Arrays.asList(objArr));

        Logger.p("Initiated " + name);
    }

    /**
     * Adds new Object to objSet.
     * @param o
     */
    public void addObject(Object o) {
        objSet.add(o);
    }

    /**
     * Removes an Object from objSet.
     * @param o
     */
    public void deleteObject(Object o) {
        Iterator<Object> iterator = objSet.iterator();
        while (iterator.hasNext()) {
            Object element = iterator.next();
            if (element.equals(o)) {
                iterator.remove();
            }
        }
    }

    /**
     * Prints all objSet elements.
     */
    public void dump() {
        Logger.p(toString());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        objSet.forEach(x -> builder.append(x.toString() + "\n"));
        builder.setLength(builder.length() - 1);

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectBox objBox = (ObjectBox) o;
        return objSet.equals(objBox.objSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objSet);
    }
}