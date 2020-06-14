package innopolis.part2.lesson15.model;

import java.util.UUID;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Dialog
 *
 * @author Stanislav_Klevtsov
 */
public class Message {

    private Long id = UUID.randomUUID().getMostSignificantBits();
    private String text;
    private Long senderId;
    private Long recipientId;
    private Long adId;

    public Message(String text, Long senderId, Long recipientId, Long adText) {
        this.text = text;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.adId = adText;
    }

    public Message(Long id, String text, Long senderId, Long recipientId, Long adText) {
        this.id = id;
        this.text = text;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.adId = adText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", senderId='" + senderId + '\'' +
                ", recipientId='" + recipientId + '\'' +
                ", adId='" + adId + '\'' +
                '}';
    }

}