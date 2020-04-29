package innopolis.part1.lesson2.task3;

/**
 * Logger
 *
 * @author Stanislav_Klevtsov
 */
public class Logger {

    public static boolean debug = false;
    public static boolean exceptionDebug = false;

    /**
     * Print
     * @param str string to log
     */
    public static void p(String str) {
        System.out.println(str);
    }

    /**
     * Print err
     * @param str
     */
    public static void e(String str) {
        if (exceptionDebug)
            System.err.println(str);
    }

    /**
     * Print object array
     * @param objects
     */
    public static void p(Object[] objects) {
        printArray(objects);
    }

    /**
     * Debug print
     * @param str
     */
    public static void d(String str) {
        if (debug)
            System.out.println(str);
    }

    /**
     * Debug print object array
     * @param str
     */
    public static void d(Object[] objects) {
        if (debug)
            printArray(objects);
    }

    /**
     * Print object array
     * @param objects
     */
    static void printArray(Object[] objects) {
        if (objects != null && objects.length > 0) {
            for(int i=0; i < objects.length; i++) {
                System.out.print(objects[i].toString() + "\n");
            }

            innopolis.part1.lesson2.task3.Logger.p("");
            return;
        }

        innopolis.part1.lesson2.task3.Logger.p("Array is empty or null!");
    }
}