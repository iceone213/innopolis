package innopolis.part2;

import innopolis.part2.lesson15.connection.ConnectionManagerJdbcImpl;
import innopolis.part2.lesson15.dao.ad.AdDaoImpl;
import innopolis.part2.lesson15.dao.user.UserDao;
import innopolis.part2.lesson15.dao.user.UserDaoJdbcImpl;
import innopolis.part2.lesson15.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * UserDaoImplTest
 *
 * @author Stanislav_Klevtsov
 */
public class UserDaoImplTest {

    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private Connection conn;
    @InjectMocks
    private UserDao userDao;

    @BeforeEach
    void setUp() throws SQLException {
        initMocks(this);
        conn = spy(ConnectionManagerJdbcImpl.getInstance().getConnection());
        preparedStatement = conn.prepareStatement(UserDaoJdbcImpl.SELECT_FROM_USERS_WHERE_ID, Statement.RETURN_GENERATED_KEYS);
        userDao = mock(UserDaoJdbcImpl.class);
        resultSet = mock(ResultSet.class);
    }

    @Test
    void getByLogin() throws SQLException {
        User user = new User("testUser", "testPass");

        when(conn.prepareStatement(UserDaoJdbcImpl.SELECT_FROM_USERS_WHERE_ID))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString(2)).thenReturn("testUser");
        when(resultSet.getString(3)).thenReturn("testPass");

        User testUser = userDao.getUserById(user.getId());
        verify(conn, times(1)).prepareStatement(UserDaoJdbcImpl.SELECT_FROM_USERS_WHERE_ID);
        verify(preparedStatement, times(1)).setString(1, user.getLogin());
        verify(preparedStatement, times(1)).executeQuery();
        assertEquals(user, testUser);

    }

}