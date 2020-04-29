package innopolis.part1.lesson2.task1;

/**
 * ExceptionTest
 *
 * @author Stanislav_Klevtsov
 */
public class ExceptionTest {

    /**
     * NullPointerException test
     * @throws NullPointerException
     */
    public static void nullPointerExp() throws NullPointerException {
        try {
            Object o = null;
            System.out.println(o.toString());
        } catch (NullPointerException e) {
            System.err.println("NullPointerException");
        }
    }

    /**
     * ArrayIndexOutOfBoundsException test
     * @throws ArrayIndexOutOfBoundsException
     */
    public static void arrayOutOfBoundsExp() throws ArrayIndexOutOfBoundsException {
        try {
            int[] intArr = new int[1];
            System.out.println(intArr[10]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("ArrayIndexOutOfBoundsException");
        }
    }

    /**
     * MyException test
     * @throws MyException
     */
    public static void myException() throws MyException {
        try {
            throw new MyException();
        } catch (MyException e) {
            System.err.println("MyException " + e.getMessage());
        }
    }
}