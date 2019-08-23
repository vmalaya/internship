package sigma.software.messagerepository.command;

import sigma.software.messagerepository.Message;

import java.util.UUID;

public class ReceiveMessageCommand {

    private final UUID friendId;
    private final Message message;

    public ReceiveMessageCommand(UUID friendId, Message message) {
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
