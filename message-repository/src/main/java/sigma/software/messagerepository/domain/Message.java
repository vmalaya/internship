package sigma.software.messagerepository.domain;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public class Message {

    private final UUID sender;
    private final UUID recipient;
    private final String body;
    private final ZonedDateTime at;

    public Message(UUID sender, UUID recipient, String body, ZonedDateTime at) {
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
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
                at.equals(message.at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, recipient, body, at);
    }
}
