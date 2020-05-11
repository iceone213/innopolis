package innopolis.part1.lesson6;

import innopolis.part1.lesson2.task2.Logger;

import java.io.*;

/**
 * Main
 *
 * @author Stanislav_Klevtsov
 */
public class FileSortMain {

    private static final String fileName = "lesson6\\note.txt";

    public static void main(String[] args) throws IOException {

        FileGenerator generator = new FileGenerator();
        String[] words = generator.randomWordArr(100);

        generator.getFiles(Const.RES_FILE_PATH, 10, 100, words, 1);
    }


}