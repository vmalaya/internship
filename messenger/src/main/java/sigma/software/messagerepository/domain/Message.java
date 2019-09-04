package sigma.software.messagerepository.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class Message {

    public enum Type {
        INCOMING, OUTGOING
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final UUID sender;
    private final UUID recipient;
    private final String body;
    private final Type type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private final ZonedDateTime at;

    @JsonCreator
    public Message(@JsonProperty("sender") UUID sender, @JsonProperty("recipient") UUID recipient,
                   @JsonProperty("body") String body, @JsonProperty("type") Type type,
                   @JsonProperty("at") ZonedDateTime at) {

        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
        this.type = type;
        this.at = at;
    }

    public UUID getSender() {
        return sender;
    }

    public UUID getRecipient() {
        return recipient;
    }

    public String getBody() {
        return body;
    }

    public Type getType() {
        return type;
    }

    public ZonedDateTime getAt() {
        return at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return sender.equals(message.sender) &&
                recipient.equals(message.recipient) &&
                body.equals(message.body) &&
                type == message.type &&
                at.equals(message.at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, recipient, body, type, at);
    }

    @Override
    public String toString() {
        return String.format("(%s): %s", at.format(formatter), body);
    }
}
