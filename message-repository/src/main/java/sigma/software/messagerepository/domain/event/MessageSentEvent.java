package sigma.software.messagerepository.domain.event;

import sigma.software.messagerepository.domain.Message;
import sigma.software.messagerepository.domain.event.api.DomainEvent;

import java.time.ZonedDateTime;
import java.util.UUID;

public class MessageSentEvent implements DomainEvent {

    private final UUID aggregateId;
    private final UUID recipient;
    private final String body;
    private final Message.Type type;
    private final ZonedDateTime at;

    public UUID getAggregateId() {
        return aggregateId;
    }

    public UUID getRecipient() {
        return recipient;
    }

    public String getBody() {
        return body;
    }

    public Message.Type getType() {
        return type;
    }

    public ZonedDateTime getAt() {
        return at;
    }

    public MessageSentEvent(UUID aggregateId, UUID recipient, String body, Message.Type type, ZonedDateTime at) {
        this.aggregateId = aggregateId;
        this.recipient = recipient;
        this.body = body;
        this.type = type;
        this.at = at;
    }
}
