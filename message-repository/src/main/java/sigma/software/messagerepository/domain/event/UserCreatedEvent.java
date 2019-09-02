package sigma.software.messagerepository.domain.event;

import sigma.software.messagerepository.domain.event.api.DomainEvent;

import java.util.UUID;

public class UserCreatedEvent implements DomainEvent {

    private final UUID aggregateId;
    private final String username;

    public UserCreatedEvent(UUID aggregateId, String username) {
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
