package innopolis.part2.lesson15;

import innopolis.part1.lesson2.task2.Logger;
import innopolis.part2.lesson15.connection.ConnectionManager;
import innopolis.part2.lesson15.connection.ConnectionManagerJdbcImpl;
import innopolis.part2.lesson15.dao.ad.AdDao;
import innopolis.part2.lesson15.dao.ad.AdDaoImpl;
import innopolis.part2.lesson15.dao.message.MessageDao;
import innopolis.part2.lesson15.dao.message.MessageDaoImpl;
import innopolis.part2.lesson15.dao.user.UserDao;
import innopolis.part2.lesson15.dao.user.UserDaoJdbcImpl;
import innopolis.part2.lesson15.model.Ad;
import innopolis.part2.lesson15.model.Message;
import innopolis.part2.lesson15.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * DBMain
 *
 * @author Stanislav_Klevtsov
 */
public class DBMain {
    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    private static final UserDao userDao = new UserDaoJdbcImpl();
    private static final AdDao adDao = new AdDaoImpl();
    private static final MessageDao messageDao = new MessageDaoImpl();


    public static void main(String[] args) {

        try (Connection connection = connectionManager.getConnection()) {

            DBManager.renewDataBase(connection); // Сброс и инициализация БД

            Logger.p("Welcome to Social_Net!");
            Logger.p("-----------------------------------------------------");

            testUserDaoImpl();
            testAdDaoImpl();
            testMessageDaoImpl();
            printAllTables();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверка CRUD-операций с пользователем
     *
     * @throws SQLException
     */
    private static void testUserDaoImpl() throws SQLException {
        Logger.p("User registration module");
        Logger.p("-");

        // Поиск по id
        User user = userDao.getUserById(1L);
        Logger.p("Getting profile for user with id = 1: " + user);

        // Добавление нового юзера
        Long uid1 = userDao.addUser(new User("Stas", "333"));
        Logger.p("New registration user with id: " + uid1);
        Long uid2 = userDao.addUser(new User("User", "111"));
        Logger.p("New registration user with id: " + uid2);

        // Удаление по id
        userDao.deleteUserById(uid2);
        Logger.p("Successfully deleted user with id = " + uid2);

        // Обновление данных по логину
        userDao.updateUserById(new User(1L, "SomeGuy", "StrongPassword2020"));

        Logger.p("User id=1 updated profile: " + userDao.getUserById(1L));

        Logger.p("-----------------------------------------------------");
    }

    /**
     * Проверка CRUD-операций с рекламой
     */
    private static void testAdDaoImpl() {
        Logger.p("Advertisement module");
        Logger.p("-");

        // Получение рекламы по id
        Ad ad1 = adDao.getAdById(1l);
        Logger.p("Getting ad with id=1: " + ad1);

        // Добавление новой рекламы в БД
        Ad ad2 = new Ad("Our new prices are awesome!");
        Long id = adDao.addAd(ad2);
        ad2.setId(id);
        Logger.p("Added new ad with id = " + id);

        // Удаление рекламы по id
        adDao.deleteAdById(1l);
        Logger.p("Successfully deleted ad with id = 1");

        // Изменение рекламы (меняем текст)
        ad2.setAdText("Boring ad...");
        adDao.updateAdById(ad2);
        Logger.p("Successfully updated ad with id = " + ad2.getId());
        Logger.p("Getting updated ad with id = " + ad2.getId() + ":" + adDao.getAdById(ad2.getId()));

        Logger.p("-----------------------------------------------------");

    }

    /**
     * Проверка CRUD-операций с сообщениями
     */
    private static void testMessageDaoImpl() {
        Logger.p("Messages module");
        Logger.p("-");

        // Получение сообщения по id
        Message msg1 = messageDao.getMessageById(1l);
        Logger.p(
                userDao.getUserById(msg1.getSenderId()).getLogin() +
                        " sent msg to " + userDao.getUserById(msg1.getRecipientId()).getLogin() +
                        " : \"" + msg1.getText() + "\"" +
                        " (Ad: " + adDao.getAdById(msg1.getAdId()).getAdText() + ")"
        );

        // Добавление нового cообщения в БД
        Message msg2 = new Message(
                "Where is moderator?",
                userDao.getUserById(1l).getId(),
                userDao.getUserById(3l).getId(),
                adDao.getAdById(2l).getId()
        );

        Long id = messageDao.addMessage(msg2);
        msg2.setId(id);
        Logger.p(
                userDao.getUserById(msg2.getSenderId()).getLogin() +
                        " sent msg to " + userDao.getUserById(msg2.getRecipientId()).getLogin() +
                        " : \"" + msg2.getText() + "\"" +
                        " (Ad: " + adDao.getAdById(msg2.getAdId()).getAdText() + ")"
        );

        // Удаление сообщения по id
        messageDao.deleteMessageById(1l);
        System.out.println("Successfully deleted msg with id = 1");

        // Редактирование сообщения (меняем текст)
        msg2.setText("Soo tired of this ad...");
        messageDao.updateMessageById(msg2);
        Logger.p("Changing the ad for msg with id = " + msg2.getId());
        Logger.p(
                userDao.getUserById(msg2.getSenderId()).getLogin() +
                        " sent msg to " + userDao.getUserById(msg2.getRecipientId()).getLogin() +
                        " : \"" + msg2.getText() + "\"" +
                        " (Ad: " + adDao.getAdById(msg2.getAdId()).getAdText() + ")"
        );

        Logger.p("-----------------------------------------------------");

    }

    public static void printAllTables() {
        Logger.p("DB");
        Logger.p("-");

        Logger.p("Messages table:");
        List<Message> messages = messageDao.getMessages();

        for (Message m : messages) {
            Logger.p(m);
        }

        Logger.p("-");

        Logger.p("Ads table:");
        List<Ad> ads = adDao.getAds();

        for (Ad ad : ads) {
            Logger.p(ad);
        }

        Logger.p("-");

        Logger.p("Users table:");
        List<User> users = userDao.getUsers();

        for (User u : users) {
            Logger.p(u);
        }

    }

}