package innopolis.part1.lesson2.task1;

/**
 * MyException
 * RuntimeException wrapper.
 *
 * @author Stanislav_Klevtsov
 */
public class MyException extends RuntimeException {

    /**
     * Test message
     */
    public MyException() {
        super("message");
    }
}