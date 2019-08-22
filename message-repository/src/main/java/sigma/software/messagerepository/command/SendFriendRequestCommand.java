package sigma.software.messagerepository.command;

import java.util.UUID;

public class SendFriendRequestCommand {

    private final UUID friendId;

    public SendFriendRequestCommand(UUID friendId) {
        this.friendId = friendId;
    }

    public UUID getFriendId() {
        return friendId;
    }
}
