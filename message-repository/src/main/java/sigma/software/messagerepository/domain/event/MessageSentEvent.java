package sigma.software.messagerepository.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import sigma.software.messagerepository.domain.Message;
import sigma.software.messagerepository.domain.event.api.DomainEvent;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public class MessageSentEvent implements DomainEvent {

    private final UUID aggregateId;
    private final UUID recipient;
    private final String body;
    private final Message.Type type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private final ZonedDateTime at;

    @JsonCreator
    public MessageSentEvent(@JsonProperty("aggregateId") UUID aggregateId,
                            @JsonProperty("recipient") UUID recipient,
                            @JsonProperty("body") String body,
                            @JsonProperty("type") Message.Type type,
                            @JsonProperty("at") ZonedDateTime at) {

        this.aggregateId = aggregateId;
        this.recipient = recipient;
        this.body = body;
        this.type = type;
        this.at = at;
    }

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

    @Override
    public String toString() {
        return "MessageSentEvent{" +
                "aggregateId=" + aggregateId +
                ", recipient=" + recipient +
                ", body='" + body + '\'' +
                ", type=" + type +
                ", at=" + at +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageSentEvent)) return false;
        MessageSentEvent that = (MessageSentEvent) o;
        return Objects.equals(aggregateId, that.aggregateId) &&
                Objects.equals(recipient, that.recipient) &&
                Objects.equals(body, that.body) &&
                type == that.type &&
                Objects.equals(at, that.at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, recipient, body, type, at);
    }
}
