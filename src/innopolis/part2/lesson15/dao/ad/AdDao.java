package innopolis.part2.lesson15.dao.ad;

import innopolis.part2.lesson15.model.Ad;

import java.util.List;

/**
 * AdDao
 *
 * @author Stanislav_Klevtsov
 */
public interface AdDao {
    Long addAd(Ad ad);

    Ad getAdById(Long id);

    List<Ad> getAds();

    boolean updateAdById(Ad ad);

    boolean deleteAdById(Long id);
}