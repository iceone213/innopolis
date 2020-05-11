package innopolis.part1.lesson6;

import innopolis.part1.lesson2.task2.Logger;
import innopolis.part1.lesson2.task2.Utils;

import java.io.File;

/**
 * FileGenerator
 *
 * @author Stanislav_Klevtsov
 */
public class FileGenerator {

    private final int MAX_SENTENCE_LENGTH = 15;
    private final int MAX_WORD_LENGTH = 15;
    private final int MAX_PARAGRAPH_LENGTH = 20;
    private final int MAX_WORDS_COUNT = 1000;

    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final char[] END_CHARS = {'.','!','?'};

    private int fileSize = 100;

    private int fileCount;
    private File[] fileArr;

    public FileGenerator() {
        Logger.p("FileGenerator created");
    }

    /**
     * Метод генерирует , N файлов с размером Size, словарем Words и сохраняет файлы в Path.
     *
     * @param path Путь сохранения файлов
     * @param n Количество файлов для генерации
     * @param size Размер файла в байтах
     * @param words Словарь для генерации текстовых файлов
     * @param probability Вероятность вхождения одного из слов Словаря в следующее предложение (1/probability).
     * @return
     */
    public void getFiles(String path, int n, int size, String[] words, int probability) {
        for (int i = 0; i < n; i++) {
            Utils.writeFile(path + String.format("file%s", i), randomParagraph(words), size);
        }

        Logger.p(String.format("Generated %s files", n));
    }

    /**
     * Создает случайный массив слов.
     *
     * @param wordCount Количество слов которые необходимо сгенерировать
     * @return Массив слов
     */
    public String[] randomWordArr(int wordCount) {

        StringBuilder sb = new StringBuilder();
        String[] wordArr = new String[wordCount];

        for (int i = 0; i < wordCount; i++) {
            sb.setLength(0);

            int wordLength = Utils.randomInt(1, MAX_WORD_LENGTH);

            for (int j = 0; j < wordLength; j++) {
                char randomChar = ALPHABET.charAt( Utils.randomInt(0, wordLength) );

                sb.append(randomChar);
            }

            char comma = ((Utils.randomFloat(0.0f, 1.0f) < 0.25f) ? ',' : '\0');
            sb.append(comma);

            wordArr[i] = sb.toString();
        }

        Logger.p("Generated new wordArr");

        return wordArr;
    }

    /**
     * Генерирует предложение.
     *
     * @param words Массив слов, использованных для создания предложения
     * @return Сгенерированное предложение
     */
    public String randomSentence(String[] words) {

        StringBuilder sb = new StringBuilder();

        int sentenceLength = Utils.randomInt(1, MAX_SENTENCE_LENGTH);

        for (int i = 0; i < sentenceLength; i++) {

            // Если первое слово, ставим заглавной перую букву
            if (i == 0) {
                String firstWord = words[Utils.randomInt(0, words.length-1)];
                firstWord = firstWord.substring(0, 1).toUpperCase() + firstWord.substring(1);

                sb.append(firstWord).append(" ");
            }

            String nextWord = words[Utils.randomInt(0, words.length-1)];
            sb.append(nextWord);

            // Если слово не последнее добавляем " ", если последнее проверяем и удаляем ','
            if (i < sentenceLength-1) {
                sb.append(" ");
            } else {
                if (nextWord.contains(","))
                    sb.setCharAt(sb.length()-1,'\0');
            }

        }

        char endChar = END_CHARS[Utils.randomInt(0, END_CHARS.length-1)];
        sb.append(endChar);

        return sb.toString();

    }

    /**
     * Создает абзац.
     *
     * @param words Массив слов, использованных для создания абзаца.
     * @return Генерируемый абзац
     */
    public String randomParagraph(String[] words) {
        StringBuilder sb = new StringBuilder();

        int paragraphLength = Utils.randomInt(1, MAX_PARAGRAPH_LENGTH);

        for (int i = 0; i < paragraphLength; i++) {
            sb.append(randomSentence(words)).append(" ");
        }

        //Разрыв строки и перенос каретки
        sb.append("\n\r");

        return sb.toString();
    }

}