package sigma.software.messagerepository.command;

import sigma.software.messagerepository.Message;

import java.util.UUID;

public class SendMessageCommand {

    private final UUID friendId;
    private final Message message;

    public SendMessageCommand(UUID friendId, Message message) {
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