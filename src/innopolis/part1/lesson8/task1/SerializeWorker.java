package innopolis.part1.lesson8.task1;

import innopolis.part1.lesson6.Const;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;

/**
 * SerializeWorker
 *
 * @author Stanislav_Klevtsov
 */
public class SerializeWorker implements Serialize {


    @Override
    public void serialize(Object obj, String file) throws IllegalAccessException, NoSuchFieldException {

        obj.getClass().getDeclaredField("type");


        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field declaredField : fields) {

            declaredField.getModifiers();
            String type = declaredField.getType().getSimpleName();
            declaredField.getName();

            declaredField.setAccessible(true); // доступ к приватному полю
            Object fldValue = declaredField.get(obj);

            try {

                DataOutputStream dos = new DataOutputStream(
                        new FileOutputStream(   Const.RES_FILE_PATH + "obj.bin"));

                ObjectOutputStream oos = new ObjectOutputStream(
                        new FileOutputStream(Const.RES_FILE_PATH + "obj.bin"));

                switch (type) {
                    case "String":
                        dos.writeUTF((String) fldValue); break;
                    case "int": dos.writeInt((int) fldValue); break;
                    case "short": dos.writeShort((short) fldValue); break;
                    case "byte": dos.writeByte((byte) fldValue); break;
                    case "long": dos.writeLong((long) fldValue); break;
                    case "float": dos.writeFloat((float) fldValue); break;
                    case "double": dos.writeDouble((double) fldValue); break;
                    default:
                        oos.writeObject(fldValue);
                }

                System.out.println("Done!");

            } catch (IOException e) {
                e.printStackTrace();
            }




            System.out.println();
        }
    }

    @Override
    public Object deSerialize(String file) {
        return null;
    }
}