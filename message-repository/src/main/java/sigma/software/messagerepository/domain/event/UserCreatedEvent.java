package sigma.software.messagerepository.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import sigma.software.messagerepository.domain.event.api.DomainEvent;

import java.util.Objects;
import java.util.UUID;

public class UserCreatedEvent implements DomainEvent {

    private final UUID aggregateId;
    private final String username;

    @JsonCreator
    public UserCreatedEvent(@JsonProperty("aggregateId") UUID aggregateId,
                            @JsonProperty("username") String username) {

        this.aggregateId = aggregateId;
        this.username = username;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "UserCreatedEvent{" +
                "aggregateId=" + aggregateId +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCreatedEvent)) return false;
        UserCreatedEvent that = (UserCreatedEvent) o;
        return Objects.equals(aggregateId, that.aggregateId) &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, username);
    }
}
