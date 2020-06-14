package innopolis.part2.lesson15.dao.user;

import innopolis.part2.lesson15.connection.ConnectionManager;
import innopolis.part2.lesson15.connection.ConnectionManagerJdbcImpl;
import innopolis.part2.lesson15.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDaoJdbcImpl
 *
 * @author Stanislav_Klevtsov
 */
public class UserDaoJdbcImpl implements UserDao {

    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    @Override
    public Long addUser(User user) {
        try {
            Connection conn = connectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO users values (DEFAULT, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();


            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    public User getUserById(Long id) {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE id = ?");

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getString(3));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM users ORDER BY id")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(
                            new User(
                                    resultSet.getLong(1),
                                    resultSet.getString(2),
                                    resultSet.getString(3))
                    );
                }
                return users;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateUserById(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE users SET login=?, password=? " +
                             "WHERE id=?")) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3, user.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUserById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM users WHERE id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}