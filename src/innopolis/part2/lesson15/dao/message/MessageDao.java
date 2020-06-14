package innopolis.part2.lesson15.dao.message;

import innopolis.part2.lesson15.model.Message;

import java.util.List;

public interface MessageDao {
    Long addMessage(Message message);

    Message getMessageById(Long id);

    List<Message> getMessages();

    boolean updateMessageById(Message message);

    boolean deleteMessageById(Long id);
}
