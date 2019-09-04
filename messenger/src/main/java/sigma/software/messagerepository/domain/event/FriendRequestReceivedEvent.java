package sigma.software.messagerepository.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import sigma.software.messagerepository.domain.event.api.DomainEvent;

import java.util.Objects;
import java.util.UUID;

public class FriendRequestReceivedEvent implements DomainEvent {

    private final UUID aggregateId;
    private final UUID senderId;

    @JsonCreator
    public FriendRequestReceivedEvent(@JsonProperty("aggregateId") UUID aggregateId,
                                      @JsonProperty("senderId") UUID senderId) {
        this.aggregateId = aggregateId;
        this.senderId = senderId;
    }

    @Override
    public UUID getAggregateId() {
        return aggregateId;
    }

    public UUID getSenderId() {
        return senderId;
    }

    @Override
    public String toString() {
        return "FriendRequestReceivedEvent{" +
                "aggregateId=" + aggregateId +
                ", senderId=" + senderId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendRequestReceivedEvent)) return false;
        FriendRequestReceivedEvent that = (FriendRequestReceivedEvent) o;
        return Objects.equals(aggregateId, that.aggregateId) &&
                Objects.equals(senderId, that.senderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, senderId);
    }
}
