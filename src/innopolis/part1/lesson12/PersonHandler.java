package innopolis.part1.lesson12;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Random;

/**
 * PersonHandler
 *
 * @author Stanislav_Klevtsov
 */
public class PersonHandler implements InvocationHandler {

    private Person person;

    public PersonHandler(Person person) {
        this.person = person;
    }

    /**
     * Перехватывает вызовы к объекту person и добавляет свое поведение.
     *
     * @param proxy  Прокси объекта person
     * @param method Вызываемый метод
     * @param args   Аргументы метода
     * @return Возвращает вызов оригинального метода
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.print(" Перехватчик " + new Random().nextInt() + " ");
        return method.invoke(person, args);
    }
}