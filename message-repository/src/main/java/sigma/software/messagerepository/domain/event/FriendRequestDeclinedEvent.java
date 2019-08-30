package sigma.software.messagerepository.domain.event;

import java.util.UUID;

public class FriendRequestDeclinedEvent implements DomainEvent {
    private final UUID aggregateId;
    private final UUID userId;

    public FriendRequestDeclinedEvent(UUID aggregateId, UUID userId) {
        this.aggregateId = aggregateId;
        this.userId = userId;
    }

    @Override
    public UUID getAggregateId() {
        return aggregateId;
    }

    public UUID getUserId() {
        return userId;
    }
}
