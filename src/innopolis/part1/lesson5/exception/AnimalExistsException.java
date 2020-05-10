package innopolis.part1.lesson5.exception;

/**
 * AnimalExistsException
 * Ошибка которая возникает в случае наличия дубликата в AnimalDAO
 *
 * @author Stanislav_Klevtsov
 */
public class AnimalExistsException extends RuntimeException {

    public AnimalExistsException(String message) {
        super(message);
    }
}