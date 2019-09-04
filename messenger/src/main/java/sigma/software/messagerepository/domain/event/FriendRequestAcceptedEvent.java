package sigma.software.messagerepository.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import sigma.software.messagerepository.domain.event.api.DomainEvent;

import java.util.Objects;
import java.util.UUID;

public class FriendRequestAcceptedEvent implements DomainEvent {

    // private final String eventName = FriendRequestAcceptedEvent.class.getSimpleName();
    private final UUID aggregateId;
    private final UUID friendId;

    @JsonCreator
    public FriendRequestAcceptedEvent(@JsonProperty("aggregateId") UUID aggregateId,
                                      @JsonProperty("friendId") UUID friendId) {
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

    @Override
    public String toString() {
        return "FriendRequestAcceptedEvent{" +
                "aggregateId=" + aggregateId +
                ", friendId=" + friendId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendRequestAcceptedEvent)) return false;
        FriendRequestAcceptedEvent that = (FriendRequestAcceptedEvent) o;
        return Objects.equals(aggregateId, that.aggregateId) &&
                Objects.equals(friendId, that.friendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, friendId);
    }
}
