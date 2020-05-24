package innopolis.part1.lesson8.task1;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * SerializeWorker
 *
 * @author Stanislav_Klevtsov
 */
public class SimpleSerializeWorker implements Serialize {

    /**
     * Сериализует объект в файл
     *
     * @param obj Объект для сериализации
     * @param file Файл для записи сериализованного объекта
     */
    @Override
    public void serialize(Object obj, String file) {

        Class<?> objectClass = obj.getClass();

        Field[] fields = objectClass.getDeclaredFields(); // Поля объекта

        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));){
            stream.writeUTF(objectClass.getName()); // Записываем имя класса
            // Записываем значения всех полей
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                stream.writeObject(fields[i].get(obj));
            }


        } catch (IOException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

    /**
     * Выполняет десериализацию объекта из файла
     *
     * @param file Файл для десериализации
     * @return Объект
     */
    @Override
    public Object deSerialize(String file) {
        Object o = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));){
            // Читаем имя класса
            String className = in.readUTF();

            Class clazz = Class.forName(className); // Класс объекта
            Constructor[] cons = clazz.getConstructors(); // Конструкторы объекта
            Class[] params = cons[0].getParameterTypes(); // Параметры конструктора, берем первый возможный

            Object[] args = new Object[params.length];
            for (int i = 0; i < params.length; i++) {

                if(params[i].isPrimitive()) {
                    String name = params[i].getName();
                    switch (name){
                        case "boolean":
                            args[i] = false;
                            break;
                        default:
                            args[i] = 0;
                            break;
                    }
                } else {
                    args[i] = null;
                }

            }

            o = clazz.getConstructor(params).newInstance(args); // Создаем новый объект с полями по умолчанию

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(o,in.readObject());
            }

        } catch (IOException | ClassNotFoundException | NoSuchMethodException |
                InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }
}