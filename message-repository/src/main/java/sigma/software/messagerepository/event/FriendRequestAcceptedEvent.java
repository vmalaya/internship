package sigma.software.messagerepository.event;

import java.util.UUID;

public class FriendRequestAcceptedEvent implements DomainEvent {
    private final UUID friendId;

    public FriendRequestAcceptedEvent(UUID friendId) {
        this.friendId = friendId;
    }

    public UUID getFriendId() {
        return friendId;
    }
}
