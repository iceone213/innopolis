package innopolis.part1.lesson3.box;

import innopolis.part1.lesson2.task2.Logger;

import java.util.*;

/**
 * MathBox
 * Used Float as basic Number type for all conversions.
 *
 * @author Stanislav_Klevtsov
 */
public class MathBox extends ObjectBox {

    private static Number numberConversionClass = Float.MIN_VALUE;

    /**
     * Constructor
     * Sets the name & populates objSet from Number[].
     * @param name
     * @param objArr
     */
    public MathBox(String name, Number[] numArr) {
        super(name, numArr);
        removeNumberDuplicates();
    }

    /**
     * Removes Number duplicates
     * First, we save current objSet to temp.
     * Second, clear current objSet.
     * Third, cast all Numbers in temp to Float and add them to objSet.
     *
     * For example, objSet {1, 1.0, 1.0f, 2} will be converted to {1.0f, 2}.
     */
    private void removeNumberDuplicates() {
        Set<Object> temp = new HashSet<>(objSet);
        objSet.clear();

        for (Object e : temp) {
            e = ((Number)e).floatValue();
            this.addObject(e);
        }
    }

    /**
     * Calculates the sum of all Numbers in objSet
     * @return sum
     */
    public Number summator() {
        Number elementsSum = 0.0;
        for (Object e : objSet) {
            elementsSum = elementsSum.floatValue() + ((Number)e).floatValue();
        }
        return elementsSum.floatValue();
    }

    /**
     * Divides all the Numbers in objSet by a divider and replaces objSet with divided values.
     * @param divider
     */
    public void splitter(Number divider) {
        Set<Object> temp = new HashSet<>(objSet);
        objSet.clear();

        for (Object e : temp) {
            e = ((Number)e).floatValue() / divider.floatValue();
            this.addObject(e);
        }
        Logger.p("Divided all " + name + " Numbers by " + divider.floatValue());
    }

    /**
     * Removes a Number in objSet with value of Integer i.
     * @param i
     */
    public void removeInt(Integer i) {
        Number removeInt = i / 1.0f;

        this.deleteObject(removeInt);
    }

    /**
     * Overrides method addObject of ObjectBox.
     * Throws an IllegalArgumentException in case if argument is any other class than Number.
     * @param o
     * @throws IllegalArgumentException
     */
    @Override
    public void addObject(Object o) throws IllegalArgumentException {
        try {
            if (o instanceof Number) {
                super.addObject(o);
            } else {
                throw new IllegalArgumentException(
                        "MathBox can only hold Numbers, \"" + o.toString() + "\" is an instance of " + o.getClass()
                );
            }
        } catch (IllegalArgumentException e) {
            Logger.e(e.getMessage());
        }

    }
}