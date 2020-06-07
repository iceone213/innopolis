package innopolis.part1.lesson12;

/**
 * Worker
 *
 * @author Stanislav_Klevtsov
 */
public class Worker implements Person {

    private String name;
    private String task;

    public Worker(String name, String task) {
        this.name = name;
        this.task = task;
    }

    /**
     * Переопределяет метод интерфейса Person.
     * Выводит в консоль инф о работнике (имя, задача).
     */
    @Override
    public void introduce() {
        System.out.println("Меня зовут " + this.name + ", я выполняю задачу: " + task);
    }


}