package sigma.software.messagerepository.event;

import java.time.ZonedDateTime;
import java.util.UUID;

public class MessageSentEvent implements DomainEvent {

    private final UUID aggregateId;
    private final UUID recipient;
    private final String message;
    private final ZonedDateTime at;

    public UUID getAggregateId() {
        return aggregateId;
    }

    public UUID getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getAt() {
        return at;
    }

    public MessageSentEvent(UUID aggregateId, UUID recipient, String message, ZonedDateTime at) {
        this.aggregateId = aggregateId;
        this.recipient = recipient;
        this.message = message;
        this.at = at;
    }
}
