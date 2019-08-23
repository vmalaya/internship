package sigma.software.messagerepository.event;

import sigma.software.messagerepository.Message;

import java.util.UUID;

public class MessageReceivedEvent implements DomainEvent {

    private final UUID friendId;
    private final Message message;

    public MessageReceivedEvent(UUID friendId, Message message) {
        this.friendId = friendId;
        this.message = message;
    }

    public UUID getFriendId() {
        return friendId;
    }

    public Message getMessage() {
        return message;
    }
}
