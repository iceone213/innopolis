package innopolis.part1.lesson2.task2;

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
     * @param obj string to log
     */
    public static void p(Object obj) {
        System.out.println(obj);
    }

    /**
     * Print err
     * @param obj
     */
    public static void e(Object obj) {
        System.err.println(obj);
    }

    /**
     * Debug print err
     * @param obj
     */
    public static void exceptionDebug(Object obj) {
        if (exceptionDebug)
            System.err.println(obj);
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
     * @param obj
     */
    public static void d(Object obj) {
        if (debug)
            System.out.println(obj);
    }

    /**
     * Debug print object array
     * @param objects
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

            p("");
            return;
        }

        p("Array is empty or null!");
    }
}