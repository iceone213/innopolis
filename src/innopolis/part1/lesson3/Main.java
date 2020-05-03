package innopolis.part1.lesson3;

import innopolis.part1.lesson3.box.MathBox;
import sun.rmi.runtime.Log;

import java.util.HashMap;

/**
 * MathBoxMain
 *
 * @author Stanislav_Klevtsov
 */
public class Main {
    public static void main(String[] args) {
        Number[] numArr = {1.0, 1, 2.0f, 10.5, 5.15f};
        Number elementsSum;
        Number splitterDivider = 2.0;

        Integer removeInt = 1;

        Logger.p("Initial numArr");
        Logger.p(numArr);

        //init
        MathBox mathBox = new MathBox("mathBox", numArr);
        MathBox mathBox1 = new MathBox("mathBox1", numArr);
        Logger.p(mathBox);

        Logger.p("mathBox elements sum: " + mathBox.summator());

        Logger.p("mathBox.equals( mathBox1 ): " + (mathBox.equals(mathBox1)));
        Logger.p("mathBox hash: " + mathBox.hashCode());
        Logger.p("mathBox1 hash: " + mathBox1.hashCode() + "\n");

        mathBox.splitter(splitterDivider);
        Logger.p(mathBox);

        Logger.p("mathBox.equals( mathBox1 ): " + (mathBox.equals(mathBox1)));
        Logger.p("mathBox hash: " + mathBox.hashCode());
        Logger.p("mathBox1 hash: " + mathBox1.hashCode() + "\n");

        Logger.p("HashMap test:");
        HashMap<String, MathBox> hashMap = new HashMap<>();
        Logger.p("Saving " + mathBox.getName() + " to HashMap");
        hashMap.put(mathBox.getName(), mathBox);
        Logger.p("HashMap obj with key = " + mathBox.getName() + "\n" + hashMap.get(mathBox.getName()) + "\n");

        Logger.p("Remove int " + removeInt + " from " + mathBox.getName());
        mathBox.removeInt(removeInt);
        Logger.p(mathBox);

        Logger.p("\nAdd another Object to " + mathBox.getName());
        mathBox.addObject("123");
        Logger.p(mathBox);
    }
}