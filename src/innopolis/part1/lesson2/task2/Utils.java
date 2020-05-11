package innopolis.part1.lesson2.task2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Utils
 *
 * @author Stanislav_Klevtsov
 */
public class Utils {

    /**
     * Generates a random integer between min (inclusive) and max (inclusive).
     *
     * @param min min value bound
     * @param max max value bound
     * @return random integer between min (inclusive) and max (inclusive).
     */
    public static int randomInt(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * Generates a random integer between min (inclusive) and max (inclusive).
     *
     * @param min min value bound
     * @param max max value bound
     * @return random integer between min (inclusive) and max (inclusive).
     */
    public static float randomFloat(float min, float max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return min + r.nextFloat() * (max - min);
    }

    public static void readFile(String filename) {
        try (FileInputStream fileInputStream = new FileInputStream(filename)) {
            System.out.println("Размер файла: " + fileInputStream.available());

            int i = -1;
            while ((i = fileInputStream.read()) != -1) {
                System.out.println((char) i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeFile(String fileName, String content, int fileSize) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] buffer = content.getBytes();
            if (buffer.length > fileSize) {
                fileOutputStream.write(buffer, 0, fileSize);
            } else {
                fileOutputStream.write(buffer, 0, buffer.length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addToFile(String fileName, String str, int fileSize) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName, true)) {
            byte[] buffer = str.getBytes();
            fileOutputStream.write(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}