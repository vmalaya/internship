package sigma.software.messagerepository.domain.event;

import java.util.UUID;

public class FriendRequestAcceptedEvent implements DomainEvent {
    private final UUID aggregateId;
    private final UUID friendId;

    public FriendRequestAcceptedEvent(UUID aggregateId, UUID friendId) {
        this.aggregateId = aggregateId;
        this.friendId = friendId;
    }

    @Override
    public UUID getAggregateId() {
        return aggregateId;
    }

    public UUID getFriendId() {
        return friendId;
    }
}
