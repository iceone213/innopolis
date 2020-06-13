package innopolis.part2.lesson15.model;

/**
 * Ad
 *
 * @author Stanislav_Klevtsov
 */
public class Ad {
    private Long id;
    private String adText;

    public Ad(Long id, String adText) {
        this.id = id;
        this.adText = adText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdText() {
        return adText;
    }

    public void setAdText(String adText) {
        this.adText = adText;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id='" + id + '\'' +
                ", adText='" + adText +
                '}';
    }
}