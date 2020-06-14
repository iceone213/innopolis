package innopolis.part2.lesson15;

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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * DBMain
 *
 * @author Stanislav_Klevtsov
 */
public class DBMain {
    private static final Logger sysLog = LogManager.getLogger();
    private static final Logger secLog = LogManager.getLogger("SecLog");
    private static final Logger bsLog = LogManager.getLogger("BsLog");

    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    private static final UserDao userDao = new UserDaoJdbcImpl();
    private static final AdDao adDao = new AdDaoImpl();
    private static final MessageDao messageDao = new MessageDaoImpl();


    public static void main(String[] args) {

        try (Connection connection = connectionManager.getConnection()) {

            DBManager.renewDataBase(connection); // Сброс и инициализация БД

            sysLog.log(Level.DEBUG, "New Social_Net started at - {}", new Date(System.currentTimeMillis()));

            testUserDaoImpl();
            testAdDaoImpl();
            testMessageDaoImpl();
//            printAllTables();

        } catch (SQLException e) {
            sysLog.log(Level.ERROR, e);
            e.printStackTrace();
        }
    }

    /**
     * Проверка CRUD-операций с пользователем
     *
     * @throws SQLException
     */
    private static void testUserDaoImpl() throws SQLException {

        sysLog.log(Level.DEBUG, "User registration module started");

        // Поиск по id
        User user = userDao.getUserById(1L);
        sysLog.log(Level.DEBUG,"Getting profile for user with id = 1: {}", user);

        // Добавление нового юзера
        Long uid1 = userDao.addUser(new User("Stas", "333"));
        sysLog.log(Level.DEBUG, "New registration user with id: {}", uid1);
        Long uid2 = userDao.addUser(new User("User", "111"));
        sysLog.log(Level.DEBUG, "New registration user with id: {}", uid2);

        // Удаление по id
        userDao.deleteUserById(uid2);
        secLog.log(Level.DEBUG, "Successfully deleted user with id = {}", uid2);

        // Обновление данных по логину
        userDao.updateUserById(new User(1L, "SomeGuy", "StrongPassword2020"));
        sysLog.log(Level.DEBUG, "User id=1 updated profile: {}", userDao.getUserById(1L));
    }

    /**
     * Проверка CRUD-операций с рекламой
     */
    private static void testAdDaoImpl() {
        sysLog.log(Level.DEBUG, "Advertisement module started");

        // Получение рекламы по id
        Ad ad1 = adDao.getAdById(1l);
        sysLog.log(Level.DEBUG, "Getting ad with id=1: {}", ad1);

        // Добавление новой рекламы в БД
        Ad ad2 = new Ad("Our new prices are awesome!");
        Long id = adDao.addAd(ad2);
        ad2.setId(id);
        sysLog.log(Level.DEBUG, "Added new ad with id = {}", id);

        // Удаление рекламы по id
        adDao.deleteAdById(1l);
        secLog.log(Level.DEBUG, "Successfully deleted ad with id = 1");

        // Изменение рекламы (меняем текст)
        ad2.setAdText("Boring ad...");
        adDao.updateAdById(ad2);
        sysLog.log(Level.DEBUG, "Successfully updated ad with id = {}", ad2.getId());
        sysLog.log(Level.DEBUG, "Getting updated ad with id = {}", ad2.getId());

    }

    /**
     * Проверка CRUD-операций с сообщениями
     */
    private static void testMessageDaoImpl() {
        sysLog.log(Level.DEBUG, "Messages module started");

        // Получение сообщения по id
        Message msg1 = messageDao.getMessageById(1l);
        sysLog.log(Level.DEBUG,
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
        sysLog.log(Level.DEBUG,
                userDao.getUserById(msg2.getSenderId()).getLogin() +
                        " sent msg to " + userDao.getUserById(msg2.getRecipientId()).getLogin() +
                        " : \"" + msg2.getText() + "\"" +
                        " (Ad: " + adDao.getAdById(msg2.getAdId()).getAdText() + ")"
        );

        // Удаление сообщения по id
        messageDao.deleteMessageById(1l);
        secLog.log(Level.DEBUG, "Successfully deleted msg with id = 1");

        // Редактирование сообщения (меняем текст)
        msg2.setText("So tired of this ad...");
        msg2.setAdId(3l);
        messageDao.updateMessageById(msg2);
        bsLog.log(Level.DEBUG, "Changing the ad for msg with id = {}", msg2.getId());
        sysLog.log(Level.DEBUG,
                userDao.getUserById(msg2.getSenderId()).getLogin() +
                        " sent msg to " + userDao.getUserById(msg2.getRecipientId()).getLogin() +
                        " : \"" + msg2.getText() + "\"" +
                        " (Ad: " + adDao.getAdById(msg2.getAdId()).getAdText() + ")"
        );
    }

    public static void printAllTables() {
        sysLog.log(Level.DEBUG, "DB");

        sysLog.log(Level.DEBUG, "Messages table:");
        List<Message> messages = messageDao.getMessages();

        for (Message m : messages) {
            sysLog.log(Level.DEBUG, m);
        }

        sysLog.log(Level.DEBUG, "-");

        sysLog.log(Level.DEBUG, "Ads table:");
        List<Ad> ads = adDao.getAds();

        for (Ad ad : ads) {
            sysLog.log(Level.DEBUG, ad);
        }

        sysLog.log(Level.DEBUG, "-");

        sysLog.log(Level.DEBUG, "Users table:");
        List<User> users = userDao.getUsers();

        for (User u : users) {
            sysLog.log(Level.DEBUG, u);
        }

    }

}