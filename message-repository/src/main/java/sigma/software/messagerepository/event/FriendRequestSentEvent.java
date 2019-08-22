package sigma.software.messagerepository.event;

import java.util.UUID;

public class FriendRequestSentEvent implements DomainEvent {

    private final UUID friendId;

    public FriendRequestSentEvent(UUID friendId) {
        this.friendId = friendId;
    }

    public UUID getFriendId() {
        return friendId;
    }
}
