package sigma.software.messagerepository.domain.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import sigma.software.messagerepository.domain.Message;
import sigma.software.messagerepository.domain.event.api.DomainEvent;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public class MessageReceivedEvent implements DomainEvent {

    private final UUID aggregateId;
    private final UUID sender;
    private final String message;
    private final Message.Type type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
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

    @Override
    public String toString() {
        return "MessageReceivedEvent{" +
                "aggregateId=" + aggregateId +
                ", sender=" + sender +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", at=" + at +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageReceivedEvent)) return false;
        MessageReceivedEvent that = (MessageReceivedEvent) o;
        return Objects.equals(aggregateId, that.aggregateId) &&
                Objects.equals(sender, that.sender) &&
                Objects.equals(message, that.message) &&
                type == that.type &&
                Objects.equals(at, that.at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, sender, message, type, at);
    }
}
