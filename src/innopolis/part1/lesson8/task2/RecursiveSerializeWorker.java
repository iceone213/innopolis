package innopolis.part1.lesson8.task2;

import innopolis.part1.lesson2.task3.Sex;
import innopolis.part1.lesson8.task1.Serialize;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * ObjectSerializeWorker
 *
 * @author Stanislav_Klevtsov
 */
public class RecursiveSerializeWorker implements Serialize {

    /**
     * Сериализует объект в файл
     *
     * @param obj Объект
     * @param file Файл для записи сериализованного объекта
     */
    @Override
    public void serialize(Object object, String file) {
        if (object == null) {
            throw new NullPointerException("Объект отсутствует!");
        } else {
            try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
                serializeObject(object, stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Получает класс и поля объекта для дальнейшей сериализации
     *
     * @param object Объект
     * @param stream Поток ObjectOutputStream
     */
    private void serializeObject(Object object, ObjectOutputStream stream) {
        Class<?> objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields(); // Поля объекта
        try {
            stream.writeUTF(objectClass.getName()); // Записываем имя класса
            // Записываем значения всех полей
            serializeFields(object, stream, fields);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Рекурсивно сериализует поля объекта в ранее созданный поток для записи
     *
     * @param obj Объект ссылочного типа
     * @param stream Поток для записи
     * @param fields Поля объекта
     */
    private void serializeFields(Object obj, ObjectOutputStream stream, Field[] fields) {
        for (Field field : fields) {

            field.setAccessible(true);
            try {
                // Если поле является примитивом записываем его в файл
                // иначе (т.е. поле объект) рекурсивно вызываем serializeObject
                if (!hasFields(field)) {
                    stream.writeObject(field.get(obj));
                } else {
                    if (field.get(obj) == null) {
                        stream.writeObject(null);
                    } else {
                        serializeObject(field.get(obj), stream);
                    }
                }
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Проверяет может ли поле иметь собственные поля (т.е. быть объектом).
     *
     * @param field Поле
     * @return Возвращает true, если поле - примитив, String или обертка над примитивным типом
     */
    private boolean hasFields(Field field) {
        Class<?> type = field.getType();
        if (type.isPrimitive()) {
            return false;
        }
        String name = type.getName();
        if (name.equals(String.class.getName()) || name.equals(Byte.class.getName()) ||
                name.equals(Short.class.getName()) || name.equals(Character.class.getName()) ||
                name.equals(Integer.class.getName()) || name.equals(Double.class.getName()) ||
                name.equals(Float.class.getName()) || name.equals(Long.class.getName()) ||
                name.equals(Boolean.class.getName()) || name.equals(Sex.class.getName())) {
            return false;
        }
        return true;
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
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            o = deserializeObject(in);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * Получет сведения о классе, конструкторе и поля объекта и запускает сериализацию
     *
     * @param in Поток ObjectInputStream
     * @return Объект
     */
    public Object deserializeObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        Object o = null;
        String className = null;

        try {
            className = in.readUTF();
        } catch (EOFException e) {
            in.readObject();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class clazz = Class.forName(className); // Класс объекта
            Constructor[] cons = clazz.getConstructors(); // Конструкторы объекта
            Class[] params = cons[0].getParameterTypes(); // Параметры конструктора, берем первый возможный

            Object[] args = getArgs(params);

            o = clazz.getConstructor(params).newInstance(args); // Создаем новый объект с полями по умолчанию

            Field[] fields = clazz.getDeclaredFields();
            deserializeFields(in, o, fields);

        } catch (IOException | ClassNotFoundException | NoSuchMethodException |
                InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * Рекурсивно сериализует поля объекта из ранее созданного потока чтения из файла
     *
     * @param in Поток чтения
     * @param o Объект
     * @param fields Поля объекта
     */
    private void deserializeFields(ObjectInputStream in, Object o, Field[] fields) throws IllegalAccessException, IOException, ClassNotFoundException {
        for (Field field : fields) {
            field.setAccessible(true);
            // Если поле является примитивом читаем его из файла и присваеваем
            // иначе (т.е. поле объект) рекурсивно вызываем deserializeObject
            if (!hasFields(field)) {
                field.set(o, in.readObject());
            } else {
                field.set(o, deserializeObject(in));
            }
        }
    }

    /**
     * Вспомогательный метод для установки агрументов конструктора по умолчанию
     *
     * @param params Параметры конструктора
     * @return Массив аргументов
     */
    private Object[] getArgs(Class[] params) {
        Object[] args = new Object[params.length];

        for (int i = 0; i < params.length; i++) {
            if (params[i].isPrimitive()) {
                String name = params[i].getName();
                if ("boolean".equals(name)) {
                    args[i] = false;
                } else {
                    args[i] = 0;
                }
            } else {
                args[i] = null;
            }
        }
        return args;
    }

}