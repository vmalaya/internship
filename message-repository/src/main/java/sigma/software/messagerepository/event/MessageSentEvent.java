package sigma.software.messagerepository.event;

import sigma.software.messagerepository.Message;

import java.util.UUID;

public class MessageSentEvent implements DomainEvent {

    private final UUID friendId;
    private final Message message;

    public MessageSentEvent(UUID friendId, Message message) {
        this.friendId = friendId;
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public UUID getFriendId() {
        return friendId;
    }
}
