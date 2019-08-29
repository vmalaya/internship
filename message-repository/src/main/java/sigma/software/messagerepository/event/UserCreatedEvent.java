package sigma.software.messagerepository.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserCreatedEvent implements DomainEvent {

    private UUID aggregateId;
    private String username;

    public UserCreatedEvent() {
    }

    @JsonCreator
    public UserCreatedEvent(@JsonProperty(required = true) UUID aggregateId,
                            @JsonProperty(required = true) String username) {

        this.aggregateId = aggregateId;
        this.username = username;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public String getUsername() {
        return username;
    }
}
