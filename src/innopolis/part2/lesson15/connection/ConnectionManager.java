package innopolis.part2.lesson15.connection;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection() throws ClassNotFoundException;
}
