package innopolis.part2.lesson15.dao.message;

import innopolis.part2.lesson15.model.Ad;
import innopolis.part2.lesson15.model.Message;

public interface MessageDao {
    Long addMessage(Message message);

    Message getMessageById(Long id);

    boolean updateMessageById(Message message);

    boolean deleteById(Long id);
}
