package sigma.software.messagerepository.event;

import java.util.UUID;

public class FriendRequestDeclinedEvent implements DomainEvent {

    private final UUID userId;

    public FriendRequestDeclinedEvent(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
