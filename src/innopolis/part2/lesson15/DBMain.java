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

            DBManager.renewDataBase(connection);//сброс и инициализация БД

            testUserDaoImpl();

//            try (Statement statement = connection.createStatement();
//                 ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
//                while (resultSet.next()) {
//                    System.out.print("id=" + resultSet.getInt("id"));
//                    System.out.print("; login=" + resultSet.getString("login"));
//                    System.out.print("; password=" + resultSet.getString("password") + "\n");
//                }
//            }


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверка CRUD-операций с пользователем
     *
     * @throws SQLException
     */
    private static void testUserDaoImpl() throws SQLException {
        UserDao impl = new UserDaoJdbcImpl();

        // Поиск по id
        User user = impl.getUserById(1L);
        Logger.p("Get data about user with id=1:");
        Logger.p(user);

        // Добавление нового юзера
        Long id = impl.addUser(new User(4L, "Stas", "333"));
        Logger.p("Added new user with id: " + id);

        // Удаление по id
        impl.deleteUserById(id);
        System.out.println("Successfully deleted user with id=" + id);

        // Обновление данных по логину
        impl.updateUserById(new User(1L, "SomeGuy", "StrongPassword2020"));

        System.out.println("Successfully updated user with id=1");
        System.out.println("New login for user with id=1 : " + impl.getUserById(1L).getLogin());

        System.out.println("-----------------------------------------------------");
    }


}