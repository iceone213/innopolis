package innopolis.part2.lesson15.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConnectionManagerJdbcImpl
 *
 * @author Stanislav_Klevtsov
 */
public class ConnectionManagerJdbcImpl implements ConnectionManager {

    public static ConnectionManager instance;

    private ConnectionManagerJdbcImpl() {
    }

    /**
     * Возвращает единственный экземпляр ConnectionManager (singleton)
     *
     * @return ConnectionManager
     */
    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManagerJdbcImpl();
        }
        return instance;
    }

    /**
     * Получает соединение с сервером и инициализирует работу с БД postgres
     * @return
     */
    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/social_net",
                    "postgres",
                    "postgres");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}