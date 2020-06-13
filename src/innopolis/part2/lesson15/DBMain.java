package innopolis.part2.lesson15;

import innopolis.part1.lesson2.task2.Logger;
import innopolis.part2.lesson15.connection.ConnectionManager;
import innopolis.part2.lesson15.connection.ConnectionManagerJdbcImpl;
import innopolis.part2.lesson15.dao.user.UserDao;
import innopolis.part2.lesson15.dao.user.UserDaoJdbcImpl;
import innopolis.part2.lesson15.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * DBMain
 *
 * @author Stanislav_Klevtsov
 */
public class DBMain {
    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    public static void main(String[] args) {
        try (Connection connection = connectionManager.getConnection()) {

            DBManager.renewDataBase(connection); // Сброс и инициализация БД

            testUserDaoImpl();
            printUserTable();


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
        UserDao userDao = new UserDaoJdbcImpl();

        // Поиск по id
        User user = userDao.getUserById(1L);
        Logger.p("Get data about user with id=1:");
        Logger.p(user);

        // Добавление нового юзера
        Long uid1 = userDao.addUser(new User("Stas", "333"));
        Logger.p("Added new user with id: " + uid1);
        Long uid2 = userDao.addUser(new User("User", "111"));
        Logger.p("Added new user with id: " + uid2);

        // Удаление по id
        userDao.deleteUserById(uid2);
        Logger.p("Successfully deleted user with id=" + uid2);

        // Обновление данных по логину
        userDao.updateUserById(new User(1L, "SomeGuy", "StrongPassword2020"));

        Logger.p("Successfully updated user with id=1");
        Logger.p("New login for user with id=1 : " + userDao.getUserById(1L).getLogin());

        Logger.p("-----------------------------------------------------");

        Logger.p("Updated table:");

        List<User> users = userDao.getUsers();
        Logger.p(users.size());

        for (User u : users) {
            Logger.p(u);
        }
    }

    public static void printUserTable() throws SQLException {
        try (Statement statement = connectionManager.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                System.out.print("id=" + resultSet.getInt("id"));
                System.out.print("; login=" + resultSet.getString("login"));
                System.out.print("; password=" + resultSet.getString("password") + "\n");
            }
        }
    }


}