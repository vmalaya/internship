package sigma.software.messagerepository.domain.event;

import java.time.ZonedDateTime;
import java.util.UUID;

public class MessageReceivedEvent implements DomainEvent {

    private final UUID aggregateId;
    private final UUID sender;
    private final String message;
    private final ZonedDateTime at;

    public MessageReceivedEvent(UUID aggregateId, UUID sender, String message, ZonedDateTime at) {
        this.aggregateId = aggregateId;
        this.sender = sender;
        this.message = message;
        this.at = at;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public UUID getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getAt() {
        return at;
    }
}
