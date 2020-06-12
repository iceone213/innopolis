package innopolis.part2.lesson15.dao.ad;

import innopolis.part2.lesson15.model.Ad;

/**
 * AdDao
 *
 * @author Stanislav_Klevtsov
 */
public interface AdDao {
    Long addAd(Ad ad);

    Ad getAdById(Long id);

    boolean updateAdById(Ad ad);

    boolean deleteAdById(Long id);
}