package sigma.software.messagerepository.domain.command;

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
