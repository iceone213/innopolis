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
    private final int PARAGRAPH_COUNT = 20;

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
    public void getFiles(String path, int n, int size, String[] words, float probability) {
        for (int i = 0; i < n; i++) {
            Utils.writeFile(
                    path + String.format("file%s", i) + ".txt",
                    randomText(words, probability, PARAGRAPH_COUNT),
                    size
            );
        }

        Logger.p(String.format("Generated %s files", n));
    }

    /**
     * Генерирует случайно слово длиной от 1 до MAX_WORD_LENGTH.
     *
     * @return Случайное слово
     */
    public String randomWord() {
        StringBuilder sb = new StringBuilder();

        int wordLength = Utils.randomInt(1, MAX_WORD_LENGTH);

        for (int j = 0; j < wordLength; j++) {
            char randomChar = ALPHABET.charAt( Utils.randomInt(0, wordLength) );

            sb.append(randomChar);
        }

        char comma = ((Utils.randomFloat(0.0f, 1.0f) < 0.25f) ? ',' : '\0');
        sb.append(comma);

        return sb.toString();
    }

    /**
     * Создает случайный массив слов.
     *
     * @param wordCount Количество слов которые необходимо сгенерировать
     * @return Массив слов
     */
    public String[] randomWordArr(int wordCount) {

        String[] wordArr = new String[wordCount];

        for (int i = 0; i < wordCount; i++) {
            wordArr[i] = randomWord();
        }

        Logger.p("Generated new wordArr");

        return wordArr;
    }

    /**
     * Генерирует предложение.
     *
     * @param words Массив слов, использованных для создания предложения
     * @param probability Вероятность попадания слова из массива слов
     * @return Сгенерированное предложение
     */
    public String randomSentence(String[] words, float probability) {

        StringBuilder sb = new StringBuilder();

        int sentenceLength = Utils.randomInt(1, MAX_SENTENCE_LENGTH);

        for (int i = 0; i < sentenceLength; i++) {

            // С определенной probability берем слово из массива слов, иначе генерируем случайное слово
            String nextWord = (
                    (Utils.randomFloat(0.0f, 1.0f) < probability) ?
                    randomWord() :
                    words[Utils.randomInt(0, words.length-1)]
            );

            // Если первое слово, ставим заглавной перую букву
            if (i == 0) {
                nextWord = nextWord.substring(0, 1).toUpperCase() + nextWord.substring(1);

            }

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
     * @param words Массив слов, использованных для создания абзаца
     * @param probability Вероятность попадания слова из массива слов
     * @return Генерируемый абзац
     */
    public String randomParagraph(String[] words, float probability) {
        StringBuilder sb = new StringBuilder();

        int paragraphLength = Utils.randomInt(1, MAX_PARAGRAPH_LENGTH);

        for (int i = 0; i < paragraphLength; i++) {
            sb.append(randomSentence(words, probability)).append(" ");
        }

        //Разрыв строки и перенос каретки
        sb.append("\n\r");

        return sb.toString();
    }

    /**
     * Генерирует случайный текст.
     *
     * @param words Массив слов, использованных для создания абзаца
     * @param probability Вероятность попадания слова из массива слов
     * @param paragraphCount Кол-во абзацев в тексте
     * @return Генерируемый текст
     */
    public String randomText(String[] words, float probability, int paragraphCount) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < paragraphCount; i++) {
            sb.append(randomParagraph(words, probability));
        }

        return sb.toString();
    }

}