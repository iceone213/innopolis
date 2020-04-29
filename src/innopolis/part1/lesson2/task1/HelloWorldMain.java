package innopolis.part1.lesson2.task1;

/**
 * HelloWorld
 * Basic HelloWorld app with NullPointerExp, ArrayOutOfBoundsExp, MyExp
 *
 * @author Stanislav_Klevtsov
 */
public class HelloWorldMain {

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        ExceptionTest.nullPointerExp();
        ExceptionTest.arrayOutOfBoundsExp();
        ExceptionTest.myException();
    }

}