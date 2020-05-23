package innopolis.part1.lesson8.task1;

public interface Serialize {

    void serialize (Object object, String file) throws IllegalAccessException, NoSuchFieldException;

    Object deSerialize(String file);

}
