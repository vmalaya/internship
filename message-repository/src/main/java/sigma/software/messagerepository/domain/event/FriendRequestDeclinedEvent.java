package sigma.software.messagerepository.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import sigma.software.messagerepository.domain.event.api.DomainEvent;

import java.util.Objects;
import java.util.UUID;

public class FriendRequestDeclinedEvent implements DomainEvent {

    private final UUID aggregateId;
    private final UUID userId;

    @JsonCreator
    public FriendRequestDeclinedEvent(@JsonProperty("aggregateId") UUID aggregateId,
                                      @JsonProperty("userId") UUID userId) {
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

    @Override
    public String toString() {
        return "FriendRequestDeclinedEvent{" +
                "aggregateId=" + aggregateId +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendRequestDeclinedEvent)) return false;
        FriendRequestDeclinedEvent that = (FriendRequestDeclinedEvent) o;
        return Objects.equals(aggregateId, that.aggregateId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, userId);
    }
}
