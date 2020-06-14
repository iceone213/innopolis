package innopolis.part2;

import innopolis.part2.lesson15.connection.ConnectionManager;
import innopolis.part2.lesson15.dao.ad.AdDaoImpl;
import innopolis.part2.lesson15.model.Ad;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * ActionsWithDBImplTest
 *
 * @author Stanislav_Klevtsov
 */
@ExtendWith(TestResultLoggerExtension.class)
public class DaoImplTest {

    private AdDaoImpl adDaoImpl;
    private ConnectionManager connManager;
    private Connection conn;
    @Mock
    private PreparedStatement preparedStatementMock;
    @Mock
    private ResultSet resultSetMock;


    @BeforeEach
    void setUp() throws SQLException {
        initMocks(this);
        connManager = mock(ConnectionManager.class);
        conn = mock(Connection.class);
        adDaoImpl = mock(AdDaoImpl.class);
    }

    @Test
    void addAd() throws SQLException {
        when(connManager.getConnection()).thenReturn(conn);
        doReturn(preparedStatementMock).when(conn).prepareStatement(AdDaoImpl.INSERT_INTO_ADS);
        when(resultSetMock.next()).thenReturn(true);

        String adText = "Ad we back again with BRAND NEW AD!!";
        Ad ad1 = new Ad(adText);

        adDaoImpl.addAd(ad1);

        verify(connManager, times(1)).getConnection();
        verify(conn, atMost(1)).prepareStatement(AdDaoImpl.INSERT_INTO_ADS);
        verify(preparedStatementMock, times(1)).setString(1, ad1.getAdText());
        verify(preparedStatementMock, times(1)).executeQuery();
    }


}