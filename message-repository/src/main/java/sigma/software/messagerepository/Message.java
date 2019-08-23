package sigma.software.messagerepository;

import java.time.ZonedDateTime;
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
}
