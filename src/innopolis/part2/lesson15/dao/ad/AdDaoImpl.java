package innopolis.part2.lesson15.dao.ad;

import innopolis.part2.lesson15.connection.ConnectionManager;
import innopolis.part2.lesson15.connection.ConnectionManagerJdbcImpl;
import innopolis.part2.lesson15.model.Ad;
import innopolis.part2.lesson15.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AdDaoImpl
 *
 * @author Stanislav_Klevtsov
 */
public class AdDaoImpl implements AdDao {

    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    public static final String INSERT_INTO_ADS = "INSERT INTO ads values (DEFAULT, ?)";
    public static final String SELECT_ALL_FROM_ADS = "SELECT * FROM ads ORDER BY id";
    public static final String SELECT_FROM_ADS_WHERE_ID = "SELECT * FROM ads WHERE id = ?";
    public static final String UPDATE_ADS_WHERE_ID = "UPDATE ads SET adText=? WHERE id=?";
    public static final String DELETE_ADS_WHERE_ID = "DELETE FROM ads WHERE id=?";

    @Override
    public Long addAd(Ad ad) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     INSERT_INTO_ADS, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, ad.getAdText());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    public List<Ad> getAds() {
        List<Ad> ads = new ArrayList<Ad>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SELECT_ALL_FROM_ADS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ads.add(
                            new Ad(
                                    resultSet.getLong(1),
                                    resultSet.getString(2)
                            )
                    );
                }
                return ads;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Ad getAdById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     SELECT_FROM_ADS_WHERE_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Ad(
                            resultSet.getLong(1),
                            resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateAdById(Ad ad) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     UPDATE_ADS_WHERE_ID)) {
            preparedStatement.setString(1, ad.getAdText());
            preparedStatement.setLong(2, ad.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAdById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     DELETE_ADS_WHERE_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}