package innopolis.part1.lesson2.task3;

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
        super("message. Person objs have same age and name.");
    }
}