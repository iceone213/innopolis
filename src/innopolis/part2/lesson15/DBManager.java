package innopolis.part2.lesson15;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

/**
 * DBManager
 *
 * @author Stanislav_Klevtsov
 */
public class DBManager {
    /**
     * Сброс и инициализация БД
     * @param db соединение с БД
     */
    public static void renewDataBase(Connection db){
        Savepoint first = null;
        Statement statement = null;
        try{
            statement = db.createStatement();
            db.setAutoCommit(false); // Ручное управление

            // Добавляем операции в батч
            statement.addBatch("DROP TABLE IF EXISTS users, messages, ads");
            statement.addBatch("CREATE TABLE users\n" +
                    "(id bigserial primary key,\n" +
                    "login VARCHAR(50) NOT NULL,\n" +
                    "password VARCHAR(50) NOT NULL\n" +
                    ");");
            statement.addBatch("INSERT INTO users (login, password)\n" +
                    "VALUES\n" +
                    "('Michael','qwerty'),\n" +
                    "('Alex','hackinganyway'), \n" +
                    "('Bruce','123');");

            first = db.setSavepoint("first"); // Savepoint после создания первой таблицы

            statement.addBatch("CREATE TABLE ads\n" +
                    "(id bigserial primary key,\n" +
                    "adText VARCHAR(50) NOT NULL\n" +
                    ");");
            statement.addBatch("INSERT INTO ads (adText)\n" +
                    "VALUES\n" +
                    "('This is an AD!'),\n" +
                    "('More AD! Check it out!');");

            statement.addBatch("CREATE TABLE messages\n" +
                    "(id bigserial primary key,\n" +
                    "text VARCHAR(100) NOT NULL,\n" +
                    "senderId bigserial NOT NULL,\n" +
                    "recipientId bigserial NOT NULL,\n" +
                    "adId bigserial NOT NULL,\n" +
                    "FOREIGN KEY (senderId) REFERENCES users(id) ON DELETE CASCADE, \n" +
                    "FOREIGN KEY (recipientId) REFERENCES users(id) ON DELETE CASCADE, \n" +
                    "FOREIGN KEY (adId) REFERENCES ads(id) ON DELETE CASCADE\n" +
                    ");");
            statement.addBatch("INSERT INTO messages\n" +
                    "(text, senderId, recipientId, adId)\n" +
                    "VALUES\n" +
                    "('Test msg', 1, 2, 2),\n" +
                    "('Some long msg', 2, 1, 2), \n" +
                    "('Hello World!', 1, 3, 2);");

            statement.executeBatch();
            db.commit(); // Ручное управление
        } catch (SQLException e){
            e.printStackTrace();
            try {
                db.rollback(first); // Пробуем откатить к точке сохранения
                statement.executeBatch();
                db.commit();
            } catch (SQLException e1){
                e1.printStackTrace();
            }
        }

    }

}