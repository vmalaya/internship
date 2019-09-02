package sigma.software.messagerepository.domain.event;

import sigma.software.messagerepository.domain.Message;
import sigma.software.messagerepository.domain.event.api.DomainEvent;

import java.time.ZonedDateTime;
import java.util.UUID;

public class MessageReceivedEvent implements DomainEvent {

    private final UUID aggregateId;
    private final UUID sender;
    private final String message;
    private final Message.Type type;
    private final ZonedDateTime at;

    public MessageReceivedEvent(UUID aggregateId, UUID sender, String message, Message.Type type, ZonedDateTime at) {
        this.aggregateId = aggregateId;
        this.sender = sender;
        this.message = message;
        this.type = type;
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

    public Message.Type getType() {
        return type;
    }

    public ZonedDateTime getAt() {
        return at;
    }
}
